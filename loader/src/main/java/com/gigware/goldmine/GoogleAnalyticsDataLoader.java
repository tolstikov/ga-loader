package com.gigware.goldmine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigware.goldmine.pojo.AnalyticsReport;
import com.gigware.goldmine.pojo.ProcessLog;
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
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class GoogleAnalyticsDataLoader {

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
    public static void main(final String[] args) {
//        try {
        for (final String viewId : PROCESS_VIEW_ID) {
            System.out.println("===============================================================================");
            System.out.println("Processing of view with id: " + viewId);
            for (final AnalyticsReport report : Reports.PROCESS_REPORTS) {
                try {
                    processReport(viewId, report);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("===============================================================================");
            }
        }
//            final DateRange dateRange = new DateRange().setStartDate("2012-01-01").setEndDate("2012-01-02");
//            final List<DateRange> dateRanges = Lists.newArrayList();
//            dateRanges.add(dateRange);
//            final List<Dimension> dimensions = Lists.newArrayList();
//            final List<Metric> metrics = Lists.newArrayList();
//
//            final Metric sessions = new Metric()
//                    .setExpression("ga:sessions")
//                    .setAlias("sessions");
////        metrics.add(sessions);
//            final Dimension pageTitle = new Dimension().setName("ga:pageTitle");
////        dimensions.add(pageTitle);
//
//            final Dimension channelGrouping = new Dimension().setName("ga:channelGrouping");
//            dimensions.add(channelGrouping);
//
//            final Metric userMetric = new Metric().setExpression("ga:users").setAlias("users");
//            metrics.add(userMetric);
//            final Metric newUserMetric = new Metric().setExpression("ga:newUsers").setAlias("newUsers");
//            metrics.add(newUserMetric);
//
//            final String viewId = CLOUDAWARE_COM__ALL_WEBSITE_DATA__VIEW_ID;
//            // Create the ReportRequest object.
//            final ReportRequest request = new ReportRequest()
//                    .setViewId(viewId)
//                    .setDateRanges(dateRanges)
//                    .setMetrics(metrics)
//                    .setDimensions(dimensions)
//                    .setPageSize(PAGE_SIZE);
//
//            final AnalyticsReporting client = new AnalyticsReporting.Builder(
//                    getHttpTransport(),
//                    JacksonFactory.getDefaultInstance(),
////                GoogleCredential.getApplicationDefault().createScoped(AnalyticsReportingScopes.all())
//                    credential.createScoped(AnalyticsReportingScopes.all())
//            ).setApplicationName("Cloudaware").build();
//            final GetReportsResponse response = client.reports().batchGet(
//                    new GetReportsRequest()
//                            .setReportRequests(ImmutableList.of(request))
//            ).execute();
//            for (final Report report : response.getReports()) {
//                final ColumnHeader header = report.getColumnHeader();
//                final List<String> dimensionHeaders = header.getDimensions();
//                final List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
//                final List<ReportRow> rows = report.getData().getRows();
//
//                if (rows == null) {
//                    System.out.println("No data found for " + viewId);
//                    return;
//                }
//
//                for (final ReportRow row : rows) {
//                    final List<String> rowDimensions = row.getDimensions();
//                    final List<DateRangeValues> rowMetrics = row.getMetrics();
//
//                    for (int i = 0; i < dimensionHeaders.size() && i < rowDimensions.size(); i++) {
//                        System.out.println(dimensionHeaders.get(i) + ": " + rowDimensions.get(i));
//                    }
//
//                    for (int j = 0; j < rowMetrics.size(); j++) {
//                        System.out.print("Date Range (" + j + "): ");
//                        final DateRangeValues values = rowMetrics.get(j);
//                        for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
//                            System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println();
    }

    private static void processReport(final String viewId, final AnalyticsReport report) throws IOException {
        final AnalyticsReporting client = getAnalyticsReporting();
        final LocalDate startDate = calculateRangeStart(viewId, report);

        for (LocalDate firstRequestDate = START; firstRequestDate.isBefore(END); firstRequestDate = firstRequestDate.plusDays(2)) {
            final String firstDate = firstRequestDate.format(DATE_TIME_FORMATTER);
            final String secondDate = firstRequestDate.plusDays(1).format(DATE_TIME_FORMATTER);
            final ReportRequest request = new ReportRequest()
                    .setViewId(viewId)
                    .setDateRanges(
                            Arrays.asList(
                                    new DateRange().setStartDate(firstDate).setEndDate(firstDate),
                                    new DateRange().setStartDate(secondDate).setEndDate(secondDate)
                            )
                    )
                    .setMetrics(report.getMetrics())
                    .setDimensions(report.getDimensions())
                    .setPageSize(PAGE_SIZE);
            GetReportsResponse response = client.reports().batchGet(
                    new GetReportsRequest()
                            .setReportRequests(ImmutableList.of(request))
            ).execute();
            if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
                throw new IllegalArgumentException("No data in response");
            }
            if (response.getReports().size() > 1) {
                throw new IllegalArgumentException("More than 1 report in response");
            }
            Report responseReport = response.getReports().get(0);
            save(viewId, report, firstDate + "__" + secondDate + "_p0", responseReport);
            while (responseReport.getNextPageToken() != null) {
                response = client.reports().batchGet(
                        new GetReportsRequest()
                                .setReportRequests(
                                        ImmutableList.of(
                                                request.setPageToken(responseReport.getNextPageToken())
                                        )
                                )
                ).execute();
                if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
                    throw new IllegalArgumentException("No data in response");
                }
                if (response.getReports().size() > 1) {
                    throw new IllegalArgumentException("More than 1 report in response");
                }
                responseReport = response.getReports().get(0);
                save(viewId, report, firstDate + "__" + secondDate + "_" + responseReport.getNextPageToken(), responseReport);
            }
        }
    }

    private static void save(final String viewId, final AnalyticsReport report, final String suffix, final Report response) throws IOException {
        final Storage storage = getStorage();
        System.out.println("  Saving report: " + report.getUniqueName() + "/" + suffix);
        final String reportContent = MAPPER.writeValueAsString(response);
        final StorageObject object = storage.objects().insert(
                OUTPUT_BUCKET_NAME,
                new StorageObject()
                        .setBucket(OUTPUT_BUCKET_NAME)
                        .setName(viewId + "/" + report.getUniqueName() + "/" + suffix + ".json"),
                new InputStreamContent("application/json", new ByteArrayInputStream(reportContent.getBytes()))
        ).execute();
        System.out.println("  object saved: " + object.getName());
    }

    private static LocalDate calculateRangeStart(final String viewId, final AnalyticsReport report) throws IOException {
        final Storage storage = getStorage();
        final Optional<StorageObject> processLogsOpt = listBucketPrefix(storage, OUTPUT_BUCKET_NAME, viewId + "/process/" + report.getUniqueName()).stream().findFirst();
        final LocalDate start;
        if (processLogsOpt.isPresent()) {
            final StorageObject processLogFile = processLogsOpt.get();
            final InputStream input = storage.objects().get(OUTPUT_BUCKET_NAME, processLogFile.getName()).executeMediaAsInputStream();
            final ProcessLog processLog = MAPPER.readValue(input, ProcessLog.class);
            final Optional<ProcessLogRecord> logRecord = processLog.findReportLog(report);
            if (logRecord.isPresent()) {
                final ProcessLogRecord reportLog = logRecord.get();
                final Optional<LocalDate> max = reportLog.getProcessedDays().stream().map(LocalDate::parse).max(Comparator.comparing(d -> d.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond()));
                start = max.map(localDate -> localDate.plusDays(1)).orElse(START);
            } else {
                start = START;
            }
        } else {
            start = START;

        }
        return start;
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
