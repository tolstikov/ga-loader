package com.gigware.goldmine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigware.goldmine.pojo.AnalyticsReport;
import com.gigware.goldmine.pojo.ProcessLogRecord;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.StorageObject;
import com.google.common.collect.ImmutableList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class GoogleAnalyticsDataLoader {

//    private static final List<AnalyticsReport> PROCESS_REPORTS = Arrays.asList(
//            new AnalyticsReport(
//                    "Acquisition -> All Traffic -> channels",
//                    "audience-all-traffic-channels",
//                    Arrays.asList(),
//                    Arrays.asList(),
//                    Arrays.asList(
//                            AnalMetrics.USERS__USERS,
//                            AnalMetrics.USERS__NEW_USERS,
//                            AnalMetrics.SESSIONS__SESSIONS,
//                            AnalMetrics.PAGE_TRACKING__PAGE_VIEWS,
//                            AnalMetrics.PAGE_TRACKING__PAGE_VIEWS_PER_SESSION,
//                            AnalMetrics.SESSIONS__AVG_SESSION_DURATION,
//                            AnalMetrics.SESSIONS__BOUNCE_RATE
//                    )
//            )
//    );

    private static final List<String> PROCESS_VIEW_ID = Arrays.asList(
            // PRODUCT__ALL_WEBSITE_DATA_VIEW_ID
            "77102263",
            // PRODUCT__USERS_VIEW_ID
            "129100799",
            // CLOUDAWARE_COM__ALL_WEBSITE_DATA__VIEW_ID
            "67352952",
            // CLOUDAWARE_COM__RAW_DATA__VIEW_ID
            "209865267"
    );

    private static final Integer PAGE_SIZE = 1000000;
    private static final LocalDate START = LocalDate.of(2012, 1, 1);
    private static final LocalDate END = LocalDate.of(2024, 6, 30);
    private static final String OUTPUT_BUCKET_NAME = "ga-reports";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static GoogleCredential CREDENTIALS;
    private static HttpTransport HTTP_TRANSPORT;
    private static AnalyticsReporting ANALYTICS_REPORTING_CLIENT;
    private static Storage STORAGE_CLIENT;

    /**
     * TODO reports list: cloudaware.com/all web site data view(фильтрованный репорт, убраны наши айпшки и спамный траффик) + raw data view(все данные без фильтров)
     * дата с 1 января 2011
     * 1. audience
     * overview - все графики ->
     * full report(geo-> language with all secondary dimensions)
     * active users (all secondary dimensions)
     * geo(language + location)
     * behavior(+ all internal stuff with all secondary dimensions)
     * technology (browser + os with all sec. dims)
     * mobile full
     * users flow....if can download
     * 2. acquisition
     * all reports that my hand can touch
     * 3. behavior - full
     * 4. conversions.goals + conversions.multi-channel funnels
     * todo product -> users(view)
     * audience -> user explorer(данные по тому где шлюхались в ланчере и продукте пользаки) Алка очень просила, но надо ли
     */
    public static void main(final String[] args) throws IOException {

        final ReportsConverter reportsConverter = new ReportsConverter("gutted");
//        final String startDate = "2023-08-01";
//        final String endDate = "2023-08-31";
        final List<AnalyticsReport> analyticsReports = reportsConverter.readReports();
        final Set<String> downloaded = downloaded();
        for (final String viewId : PROCESS_VIEW_ID) {
            System.out.println("===============================================================================");
            System.out.println("Processing of view with id: " + viewId);

            for (AnalyticsReport analyticsReport : analyticsReports) {
                final List<Dimension> dimensions = Lists.newArrayList(analyticsReport.getDimensions());
                dimensions.add(new Dimension().setName("ga:date"));
                for (LocalDate firstRequestDate = START; firstRequestDate.isBefore(END); firstRequestDate = firstRequestDate.plusYears(1)) {
                    final String startDate = firstRequestDate.format(DATE_TIME_FORMATTER);
                    final String endDate = firstRequestDate.plusYears(1).minusDays(1).format(DATE_TIME_FORMATTER);
                    System.out.println("Process: " + analyticsReport.getUniqueName() + " from: " + startDate + " to: " + endDate);
                    if (!analyticsReport.getSecondaryDimensions().isEmpty()) {
                        for (final Dimension secondary : analyticsReport.getSecondaryDimensions()) {
                            List<Dimension> requestDimensions = Lists.newArrayList(dimensions);
                            requestDimensions.add(secondary);
                            downloadSingle(viewId, requestDimensions, analyticsReport.getMetrics(), startDate, endDate, analyticsReport.getUniqueName() + "/" + secondary.getName(), downloaded);
                        }
                    } else {
                        downloadSingle(viewId, dimensions, analyticsReport.getMetrics(), startDate, endDate, analyticsReport.getUniqueName(), downloaded);
                    }
                }
            }
            System.out.println("===============================================================================");
        }
        System.out.println();
    }

    private static void downloadSingle(final String viewId, final List<Dimension> dimensions, final List<Metric> metrics, final String startDate,
                                       final String endDate, final String name, final Set<String> downloaded) {

        int retryCount = 5;

        do {
            final AnalyticsReporting client = getAnalyticsReporting();
            //            dimensions.add(EnumDimensions.TIME__DATE.getDimension());
            final ReportRequest request = new ReportRequest()
                    .setViewId(viewId)
                    .setDateRanges(
                            Arrays.asList(
                                    new DateRange().setStartDate(startDate).setEndDate(endDate)
                            )
                    )
                    .setMetrics(metrics)
                    .setDimensions(dimensions)
                    .setPageSize(PAGE_SIZE);
            try {
                String nextPageToken = null;
                GetReportsResponse response;
                do {
                    String key = viewId + "/" + name.toLowerCase() + "/" + startDate + "__" + endDate + "_" + nextPageToken + ".json";
                    if (downloaded.contains(key)) {
                        return;
                    }
                    response = client.reports().batchGet(
                            new GetReportsRequest()
                                    .setReportRequests(
                                            ImmutableList.of(
                                                    request.setPageToken(nextPageToken)
                                            )
                                    )
                    ).execute();
                    if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
                        throw new IllegalArgumentException("No data in response");
                    }
                    if (response.getReports().size() > 1) {
                        throw new IllegalArgumentException("More than 1 report in response");
                    }
                    Report responseReport = response.getReports().get(0);
                    String reportContent = MAPPER.writeValueAsString(responseReport);
//                save(viewId + "/" + group.name().toLowerCase() + "_deprecated/" + rangeStartDate + "__" + rangeEndDate + ".json", reportContent);
                    save(viewId + "/" + name.toLowerCase() + "/" + startDate + "__" + endDate + "_" + nextPageToken + ".json", reportContent);
                    nextPageToken = responseReport.getNextPageToken();
                    if (nextPageToken != null && Integer.valueOf(nextPageToken) < 1) {
                        System.out.println("Invalid value of nextPageToken: " + nextPageToken);
                        nextPageToken = null;
                    }
                } while (nextPageToken != null);
                break;
            } catch (IOException e) {
                System.out.println("retry" + retryCount + " ex: " + e.getMessage());
                retryCount--;
            }
        } while (retryCount > 0);

    }

    private static void save(final String path, final String content) throws IOException {
        final Storage storage = getStorage();
        final StorageObject object = storage.objects().insert(
                OUTPUT_BUCKET_NAME,
                new StorageObject()
                        .setBucket(OUTPUT_BUCKET_NAME)
                        .setName(path),
                new InputStreamContent("application/json", new ByteArrayInputStream(content.getBytes()))
        ).execute();
        System.out.println("====object saved: " + object.getName());
    }

    private static Set<String> downloaded() throws IOException {
        final Storage storage = getStorage();
        final List<StorageObject> objects = listBucketPrefix(storage, OUTPUT_BUCKET_NAME, "");
        return objects.stream().map(StorageObject::getName).collect(Collectors.toSet());
    }

    private static AnalyticsReporting getAnalyticsReporting() {
        if (ANALYTICS_REPORTING_CLIENT == null) {
            ANALYTICS_REPORTING_CLIENT = new AnalyticsReporting.Builder(
                    getHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    getCredentials().createScoped(AnalyticsReportingScopes.all())
            ).setApplicationName("Cloudaware").build();
        }
        return ANALYTICS_REPORTING_CLIENT;
    }

    private static Storage getStorage() {
        if (STORAGE_CLIENT == null) {
            STORAGE_CLIENT = new Storage.Builder(
                    getHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    getCredentials().createScoped(Arrays.asList(StorageScopes.CLOUD_PLATFORM))
            ).setApplicationName("Cloudaware").build();
        }
        return STORAGE_CLIENT;
    }

    private static HttpTransport getHttpTransport() {
        if (HTTP_TRANSPORT == null) {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            } catch (GeneralSecurityException | IOException expected) {
                expected.printStackTrace();
            }
        }
        return HTTP_TRANSPORT;
    }

    private static GoogleCredential getCredentials() {
        if (CREDENTIALS == null) {
            try {
                CREDENTIALS = GoogleCredential.fromStream(GoogleAnalyticsDataLoader.class.getResourceAsStream("/ca-keys.json"));
//                CREDENTIALS = GoogleCredential.getApplicationDefault().createScoped(AnalyticsReportingScopes.all());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CREDENTIALS;
    }

    public static List<StorageObject> listBucketPrefix(final Storage client, final String bucket, final String path) throws IOException {
        com.google.api.services.storage.model.Objects workingObjects;
        String nextToken = null;
        final List<StorageObject> listing = Lists.newArrayList();
        do {
            workingObjects = client.objects().list(bucket).setPrefix(path).setPageToken(nextToken).execute();
            if (workingObjects.getItems() != null) {
                listing.addAll(workingObjects.getItems());
            }
            nextToken = workingObjects.getNextPageToken();
        } while (nextToken != null);
        return listing;
    }

}
