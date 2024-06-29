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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GoogleAnalyticsDataLoader {

    private static final List<AnalyticsReport> PROCESS_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Overview",
                    "audience-overview",
                    Arrays.asList(),
                    Arrays.asList(
                            AnalMetrics.USERS__USERS,
                            AnalMetrics.USERS__NEW_USERS,
                            AnalMetrics.SESSIONS__SESSIONS,
                            AnalMetrics.PAGE_TRACKING__PAGE_VIEWS,
                            AnalMetrics.PAGE_TRACKING__PAGE_VIEWS_PER_SESSION,
                            AnalMetrics.SESSIONS__AVG_SESSION_DURATION,
                            AnalMetrics.SESSIONS__BOUNCE_RATE
                    )
            )
    );

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

        final ReportsConverter reportsConverter = new ReportsConverter();
        final List<AnalyticsReport> analyticsReports = reportsConverter.readReports();
        for (AnalyticsReport analyticsReport : analyticsReports){
            downloadSingle("67352952", analyticsReport.getDimensions(), analyticsReport.getMetrics(), "2013-10-01","2013-10-31", analyticsReport.getUniqueName());
        }
//        testDate();
//        try {

//        downloadSingle("67352952",
////                Stream.of(
////                        EnumDimensions.TIME__DATE
////                ).map(EnumDimensions::getDimension).collect(Collectors.toList()),
//                Stream.of(
//                        "ga:date"
//                ).map(d->new Dimension().setName(d)).collect(Collectors.toList()),
//                Stream.of(
////                        EnumMetrics.SESSION__SESSIONS,
////                        EnumMetrics.SESSION__BOUNCES,
////                        EnumMetrics.USER__1_DAY_ACTIVE_USERS,
////                        EnumMetrics.USER__1_DAY_ACTIVE_USERS,
//                        EnumMetrics.USER__14_DAY_ACTIVE_USERS
//                ).map(EnumMetrics::getMetric).collect(Collectors.toList()),
//                "2013-10-01",
//                "2013-10-31",
//                "test_ug");
//        for (final String viewId : PROCESS_VIEW_ID) {
//            System.out.println("===============================================================================");
//            System.out.println("Processing of view with id: " + viewId);
////            try {
//
//////                processView(viewId);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//            for (final AnalyticsReport report : PROCESS_REPORTS) {
//                try {
//                    processFullReport(viewId, report);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("===============================================================================");
//        }
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

    private static void testDate() throws IOException {
        final AnalyticsReporting client = getAnalyticsReporting();
        int requestCount = 0;
        final ReportRequest request = new ReportRequest()
                .setViewId("77102263")
                .setDateRanges(
                        Arrays.asList(
                                new DateRange().setStartDate("2012-01-01").setEndDate("2012-12-31"),
                                new DateRange().setStartDate("2013-01-01").setEndDate("2013-12-31")
                        )
                )
                .setMetrics(Arrays.asList(
                        AnalMetrics.USERS__USERS,
                        AnalMetrics.USERS__NEW_USERS,
                        AnalMetrics.SESSIONS__SESSIONS,
                        AnalMetrics.PAGE_TRACKING__PAGE_VIEWS,
                        AnalMetrics.PAGE_TRACKING__PAGE_VIEWS_PER_SESSION,
                        AnalMetrics.SESSIONS__AVG_SESSION_DURATION,
                        AnalMetrics.SESSIONS__BOUNCE_RATE
                ))
                .setDimensions(Arrays.asList(
                        new Dimension().setName("ga:date")
                ))
                .setPageSize(PAGE_SIZE);

        GetReportsResponse response = client.reports().batchGet(
                new GetReportsRequest()
                        .setReportRequests(ImmutableList.of(request))
        ).execute();
        final List<Report> reports = new ArrayList<>();
        System.out.println("====requests: " + ++requestCount + "====");
        if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
            throw new IllegalArgumentException("No data in response");
        }
        if (response.getReports().size() > 1) {
            throw new IllegalArgumentException("More than 1 report in response");
        }
        Report responseReport = response.getReports().get(0);
        reports.add(responseReport);
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
            reports.add(responseReport);
            System.out.println("====requests: " + ++requestCount + "====");
        }
        System.out.println();
    }

    private static void processView(final String viewId) {
        for (final EnumGroup report : EnumGroup.values()) {
            if (report.getDimensions().size() < 9) {
//                simpleDownload(viewId, report);
            } else {
                calculatedDownload(report);
            }
        }
        System.out.println();
    }

    private static void downloadSingle(final String viewId, final List<Dimension> dimensions, final List<Metric> metrics, final String startDate,
                                       final String endDate, final String name) {
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
            String reportContent = MAPPER.writeValueAsString(responseReport);
//                save(viewId + "/" + group.name().toLowerCase() + "_deprecated/" + rangeStartDate + "__" + rangeEndDate + ".json", reportContent);
            save(viewId + "/" + name.toLowerCase() + "/" + startDate + "__" + endDate + ".json", reportContent);
            // todo logging
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
                save(viewId + "/" + name.toLowerCase() + "/" + startDate + "__" + endDate + "_" + responseReport.getNextPageToken() + ".json", reportContent);
                // todo logging
            }
        } catch (IOException e) {
            // todo retry logic
            e.printStackTrace();
        }
    }

    private static void simpleDownload(final String viewId, final List<EnumDimensions> dimensionsIn, final List<EnumMetrics> metricsIn, final String name) {
        final AnalyticsReporting client = getAnalyticsReporting();
        // todo think about continue
        final LocalDate startDate = START;
        int requestCount = 0;
        for (LocalDate firstRequestDate = startDate; firstRequestDate.isBefore(END); firstRequestDate = firstRequestDate.plusYears(1)) {
            final String rangeStartDate = firstRequestDate.format(DATE_TIME_FORMATTER);
            final LocalDate calculatedEnd = firstRequestDate.plusDays(firstRequestDate.lengthOfYear() - 1);
            final String rangeEndDate;
            if (calculatedEnd.isBefore(END)) {
                rangeEndDate = calculatedEnd.format(DATE_TIME_FORMATTER);
            } else {
                rangeEndDate = END.format(DATE_TIME_FORMATTER);
            }
            final List<Dimension> dimensions = dimensionsIn.stream().map(EnumDimensions::getDimension).collect(Collectors.toList());
//            dimensions.add(EnumDimensions.TIME__DATE.getDimension());
            downloadSingle(viewId, dimensionsIn.stream().map(EnumDimensions::getDimension).toList(), metricsIn.stream().map(EnumMetrics::getMetric).toList(), rangeStartDate, rangeEndDate, name);
        }
        System.out.println();
    }

//    private static void simpleDownload(final String viewId, final EnumGroup group) {
//        final AnalyticsReporting client = getAnalyticsReporting();
//        // todo think about continue
//        final LocalDate startDate = START;
//        int requestCount = 0;
//        for (LocalDate firstRequestDate = startDate; firstRequestDate.isBefore(END); firstRequestDate = firstRequestDate.plusYears(1)) {
//            final String rangeStartDate = firstRequestDate.format(DATE_TIME_FORMATTER);
//            final LocalDate calculatedEnd = firstRequestDate.plusDays(firstRequestDate.lengthOfYear() - 1);
//            final String rangeEndDate;
//            if (calculatedEnd.isBefore(END)) {
//                rangeEndDate = calculatedEnd.format(DATE_TIME_FORMATTER);
//            } else {
//                rangeEndDate = END.format(DATE_TIME_FORMATTER);
//            }
//            final List<Dimension> dimensions = group.getDimensions().stream().map(EnumDimensions::getDimension).collect(Collectors.toList());
//            dimensions.add(EnumDimensions.TIME__DATE.getDimension());
//            final ReportRequest request = new ReportRequest()
//                    .setViewId(viewId)
//                    .setDateRanges(
//                            Arrays.asList(
//                                    new DateRange().setStartDate(rangeStartDate).setEndDate(rangeEndDate)
//                            )
//                    )
//                    .setMetrics(group.getMetrics().stream().map(EnumMetrics::getMetric).collect(Collectors.toList()))
//                    .setDimensions(dimensions)
//                    .setPageSize(PAGE_SIZE);
//            try {
//                GetReportsResponse response = client.reports().batchGet(
//                        new GetReportsRequest()
//                                .setReportRequests(ImmutableList.of(request))
//                ).execute();
//                System.out.println("====requests: " + ++requestCount + "====");
//                if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
//                    throw new IllegalArgumentException("No data in response");
//                }
//                if (response.getReports().size() > 1) {
//                    throw new IllegalArgumentException("More than 1 report in response");
//                }
//                Report responseReport = response.getReports().get(0);
//                String reportContent = MAPPER.writeValueAsString(responseReport);
//                save(viewId + "/" + group.name().toLowerCase() + "_deprecated/" + rangeStartDate + "__" + rangeEndDate + ".json", reportContent);
//                // todo logging
//                while (responseReport.getNextPageToken() != null) {
//                    response = client.reports().batchGet(
//                            new GetReportsRequest()
//                                    .setReportRequests(
//                                            ImmutableList.of(
//                                                    request.setPageToken(responseReport.getNextPageToken())
//                                            )
//                                    )
//                    ).execute();
//                    if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
//                        throw new IllegalArgumentException("No data in response");
//                    }
//                    if (response.getReports().size() > 1) {
//                        throw new IllegalArgumentException("More than 1 report in response");
//                    }
//                    responseReport = response.getReports().get(0);
//                    save(viewId + "/" + group.name().toLowerCase() + "/" + rangeStartDate + "__" + rangeEndDate + "_" + responseReport.getNextPageToken() + ".json", reportContent);
//                    // todo logging
//                }
//            } catch (IOException e) {
//                // todo retry logic
//                e.printStackTrace();
//            }
//        }
//        System.out.println();
//    }

    private static void calculatedDownload(final EnumGroup group) {

    }

    private static void processFullReport(final String viewId, final AnalyticsReport report) throws IOException {
        final AnalyticsReporting client = getAnalyticsReporting();
        final String startDate = START.format(DATE_TIME_FORMATTER);
        final String endDate = END.format(DATE_TIME_FORMATTER);
        final List<Dimension> dimensions = report.getDimensions().stream().collect(Collectors.toList());
        dimensions.add(EnumDimensions.TIME__DATE.getDimension());
        final ReportRequest request = new ReportRequest()
                .setViewId(viewId)
                .setDateRanges(
                        Arrays.asList(
                                new DateRange().setStartDate(startDate).setEndDate(endDate)
                        )
                )
                .setMetrics(report.getMetrics())
                .setDimensions(dimensions)
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
        String content = MAPPER.writeValueAsString(responseReport);
        save(viewId + "/" + report.getUniqueName() + "/p0.json", content);
        int requestCount = 0;
        System.out.println("====requests: " + ++requestCount + "====");
        while (responseReport.getNextPageToken() != null) {
            String nextPage = responseReport.getNextPageToken();
            response = client.reports().batchGet(
                    new GetReportsRequest()
                            .setReportRequests(
                                    ImmutableList.of(
                                            request.setPageToken(nextPage)
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
            save(viewId + "/" + report.getUniqueName() + "/" + nextPage + ".json", content);
//            save(viewId, report, startDate + "__" + secondDate + "_" + responseReport.getNextPageToken(), responseReport);
//            updateLog(viewId, report, Arrays.asList(firstDate, secondDate));
            System.out.println("====requests: " + ++requestCount + "====");
        }
    }

    private static void processReport(final String viewId, final AnalyticsReport report) throws IOException {
        final AnalyticsReporting client = getAnalyticsReporting();
        final LocalDate startDate = calculateRangeStart(viewId, report);
        int requestCount = 0;
        for (LocalDate firstRequestDate = startDate; firstRequestDate.isBefore(END); firstRequestDate = firstRequestDate.plusDays(2)) {
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
            System.out.println("====requests: " + requestCount++ + "====");
            if (response == null || response.getReports() == null || response.getReports().isEmpty()) {
                throw new IllegalArgumentException("No data in response");
            }
            if (response.getReports().size() > 1) {
                throw new IllegalArgumentException("More than 1 report in response");
            }
            Report responseReport = response.getReports().get(0);
            save(viewId, report, firstDate + "__" + secondDate + "_p0", responseReport);
            updateLog(viewId, report, Arrays.asList(firstDate, secondDate));
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
                updateLog(viewId, report, Arrays.asList(firstDate, secondDate));
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

    private static void save(final String path, final String content) throws IOException {
        final Storage storage = getStorage();
        System.out.println("====Saving report: " + path);
        final StorageObject object = storage.objects().insert(
                OUTPUT_BUCKET_NAME,
                new StorageObject()
                        .setBucket(OUTPUT_BUCKET_NAME)
                        .setName(path),
                new InputStreamContent("application/json", new ByteArrayInputStream(content.getBytes()))
        ).execute();
        System.out.println("====object saved: " + object.getName());
    }

    private static void updateLog(final String viewId, final AnalyticsReport report, final List<String> processedDays) throws IOException {
        final Storage storage = getStorage();
        final Optional<StorageObject> processLogsOpt = listBucketPrefix(storage, OUTPUT_BUCKET_NAME, viewId + "/process-" + report.getUniqueName()).stream().findFirst();
        if (processLogsOpt.isPresent()) {
            final StorageObject storageObject = processLogsOpt.get();
            final String objectName = storageObject.getName();
            final InputStream input = storage.objects().get(OUTPUT_BUCKET_NAME, objectName).executeMediaAsInputStream();
            final ProcessLogRecord processLog = MAPPER.readValue(input, ProcessLogRecord.class);
            processLog.getProcessedDays().addAll(processedDays);
            final String updated = MAPPER.writeValueAsString(processLog);
            storage.objects().delete(OUTPUT_BUCKET_NAME, objectName);
            final StorageObject object = storage.objects().insert(
                    OUTPUT_BUCKET_NAME,
                    new StorageObject()
                            .setBucket(OUTPUT_BUCKET_NAME)
                            .setName(objectName),
                    new InputStreamContent("application/json", new ByteArrayInputStream(updated.getBytes()))
            ).execute();
        } else {
            final ProcessLogRecord log = new ProcessLogRecord(report.getUniqueName(), processedDays);
            final String content = MAPPER.writeValueAsString(log);
            final StorageObject object = storage.objects().insert(
                    OUTPUT_BUCKET_NAME,
                    new StorageObject()
                            .setBucket(OUTPUT_BUCKET_NAME)
                            .setName(viewId + "/process-" + report.getUniqueName() + ".log"),
                    new InputStreamContent("application/json", new ByteArrayInputStream(content.getBytes()))
            ).execute();
            System.out.println("  object saved: " + object.getName());
        }
    }

    private static LocalDate calculateRangeStart(final String viewId, final AnalyticsReport report) throws IOException {
        final Storage storage = getStorage();
        final Optional<StorageObject> processLogsOpt = listBucketPrefix(storage, OUTPUT_BUCKET_NAME, viewId + "/process-" + report.getUniqueName()).stream().findFirst();
        final LocalDate start;
        if (processLogsOpt.isPresent()) {
            final StorageObject processLogFile = processLogsOpt.get();
            final InputStream input = storage.objects().get(OUTPUT_BUCKET_NAME, processLogFile.getName()).executeMediaAsInputStream();
            final ProcessLogRecord processLog = MAPPER.readValue(input, ProcessLogRecord.class);
            final Optional<LocalDate> max = processLog.getProcessedDays().stream().map(LocalDate::parse).max(Comparator.comparing(d -> d.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond()));
            start = max.map(localDate -> localDate.plusDays(1)).orElse(START);
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

    private static final class AnalDimensions {
        public static final Dimension USERS__USER_TYPE = new Dimension().setName("ga:userType");
        public static final Dimension USERS__COUNT_OF_SESSIONS = new Dimension().setName("ga:sessionCount");
        public static final Dimension USERS__DAYS_SINCE_LAST_SESSIONS = new Dimension().setName("ga:daysSinceLastSession");

        public static final Dimension SYSTEM__LANGUAGE = new Dimension().setName("ga:language");

        public static final Dimension SESSION__SESSION_DURATION = new Dimension().setName("ga:sessionDurationBucket");

        public static final Dimension PAGE_TRACKING__HOSTNAME = new Dimension().setName("ga:hostname");
        public static final Dimension PAGE_TRACKING__PAGE_PATH = new Dimension().setName("ga:pagePath");
        public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_1 = new Dimension().setName("ga:pagePathLevel1");
        public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_2 = new Dimension().setName("ga:pagePathLevel2");
        public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_3 = new Dimension().setName("ga:pagePathLevel3");
        public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_4 = new Dimension().setName("ga:pagePathLevel4");
        public static final Dimension PAGE_TRACKING__PAGE_TITLE = new Dimension().setName("ga:pageTitle");
        public static final Dimension PAGE_TRACKING__LANDING_PATE = new Dimension().setName("ga:landingPagePath");
        public static final Dimension PAGE_TRACKING__SECOND_PAGE = new Dimension().setName("ga:secondPagePath");

    }

    private static final class AnalMetrics {
        public static final Metric USERS__USERS = new Metric().setExpression("ga:users").setAlias("users");
        public static final Metric USERS__NEW_USERS = new Metric().setExpression("ga:newUsers").setAlias("newUsers");

        public static final Metric SESSIONS__SESSIONS = new Metric().setExpression("ga:sessions").setAlias("sessions");
        public static final Metric SESSIONS__BOUNCES = new Metric().setExpression("ga:bounces").setAlias("bounces");
        public static final Metric SESSIONS__BOUNCE_RATE = new Metric().setExpression("ga:bounceRate").setAlias("bounceRate");
        public static final Metric SESSIONS__SESSION_DURATION = new Metric().setExpression("ga:sessionDuration").setAlias("sessionDuration");
        public static final Metric SESSIONS__AVG_SESSION_DURATION = new Metric().setExpression("ga:avgSessionDuration").setAlias("avgSessionDuration");

        public static final Metric PAGE_TRACKING__PAGE_VALUE = new Metric().setExpression("ga:pageValue").setAlias("pageValue");
        public static final Metric PAGE_TRACKING__ENTRANCES = new Metric().setExpression("ga:entrances").setAlias("entrances");
        public static final Metric PAGE_TRACKING__ENTRANCES_PAGEVIEWS = new Metric().setExpression("ga:entranceRate").setAlias("entranceRate");
        public static final Metric PAGE_TRACKING__PAGE_VIEWS = new Metric().setExpression("ga:pageviews").setAlias("pageViews");
        public static final Metric PAGE_TRACKING__PAGE_VIEWS_PER_SESSION = new Metric().setExpression("ga:pageviewsPerSession").setAlias("pageViewsPerSession");
        public static final Metric PAGE_TRACKING__UNIQUE_PAGE_VIEWS = new Metric().setExpression("ga:uniquePageviews").setAlias("uniquePageViews");
        public static final Metric PAGE_TRACKING__TIME_ON_PAGE = new Metric().setExpression("ga:timeOnPage").setAlias("timeOnPage");
        public static final Metric PAGE_TRACKING__AVG_TIME_ON_PAGE = new Metric().setExpression("ga:avgTimeOnPage").setAlias("avgTimeOnPage");
        public static final Metric PAGE_TRACKING__EXISTS = new Metric().setExpression("ga:exits").setAlias("exits");
        public static final Metric PAGE_TRACKING__EXIT_RATE = new Metric().setExpression("ga:exitRate").setAlias("exitRate");
    }

}
