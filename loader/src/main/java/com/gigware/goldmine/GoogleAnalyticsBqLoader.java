package com.gigware.goldmine;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigware.goldmine.pojo.ReportBq;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryRequest;
import com.google.api.services.bigquery.model.ErrorProto;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationLoad;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.JobStatus;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GoogleAnalyticsBqLoader {
    private static final List<String> VIEW_TYPES = Arrays.asList(
            "77102263",
            "129100799",
            "67352952",
            "209865267"
    );
    private static final List<String> REPORT_SCHEMA_TYPES = Arrays.asList(
            "acquisition",
            "audience",
            "behavior"
    );
    private static final String BUCKET_NAME = "ga-reports";
    private static final String CREDENTIAL_BQ_PATH = "/ca-keys-bq.json";
//    private static final String CREDENTIAL_DEV_PATH = "/ca-keys-dev.json";
    private static final String DATE_DIMENSION = "ga:date";
    private static final String CSV_POSTFIX = "_csv.csv";
    private static GoogleCredential CREDENTIALS;
    private static HttpTransport HTTP_TRANSPORT;
    private static Storage STORAGE_CLIENT;
    private static Bigquery BQ_CLIENT;
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void main(final String[] args) {
        try {
            execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void execute() throws IOException, InterruptedException {
        final boolean overrideCsv = false;              //перезаписывать ли существующие CSV
        final boolean overrideBqTable = false;          //перезаписывать таблицу или добавлять записи в конец
        final boolean needCreateCsv = false;            //нужно ли создавать CSV
        final boolean needBqUpload = true;              //нужно ли загружать в BQ
        final Storage storage = getStorage(CREDENTIAL_BQ_PATH);
        for (final String view : VIEW_TYPES) {
            for (final String schema : REPORT_SCHEMA_TYPES) {
                System.out.println("########################################################################");
                System.out.println("Start processing reports in view=" + view + " and schema=" + schema);
                processViewSchemaReports(storage, view, schema, overrideCsv, overrideBqTable, needCreateCsv, needBqUpload);
                System.out.println("Rows saved for reports in view=" + view + " and schema=" + schema);
            }
        }

        System.out.println("All report processed!");
    }

    private static void processViewSchemaReports(
            final Storage storage,
            final String view,
            final String schema,
            final boolean overrideCsv,
            final boolean overrideBqTable,
            final boolean needCreateCsv,
            final boolean needBqUpload
    ) throws IOException, InterruptedException {
        final List<StorageObject> objects = listBucketPrefix(storage, BUCKET_NAME, view + "/" + schema);
        final List<StorageObject> filtered = objects.stream().filter(it -> it.getName().endsWith(".json")).toList();
        final List<StorageObject> created = objects.stream().filter(it -> it.getName().endsWith(".csv")).toList();
        if (needCreateCsv) {
            final Set<String> reportNames = filtered.stream().map(StorageObject::getName).collect(Collectors.toSet());
            int count = 0;
            for (final String reportName : reportNames) {
                count++;
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println("Report #" + count + " of " + reportNames.size() + " with name='" + reportName + "' start processing!");
                final String csvPath;
                if (reportName.endsWith(".json")) {
                    csvPath = reportName.substring(0, reportName.length() - 5) + CSV_POSTFIX;
                } else {
                    csvPath = reportName;
                }
                StorageObject csvFile;
                try {
                    csvFile = storage.objects().get(BUCKET_NAME, csvPath).execute();
                } catch (Exception ex) {
                    csvFile = null;
                }
                boolean isCvsExists = csvFile != null && csvFile.getSize().intValue() > 10;
                if (overrideCsv || !isCvsExists) {
                    final String content = getFileContent(storage, BUCKET_NAME, reportName);
                    final ReportBq report = MAPPER.readValue(content, ReportBq.class);
                    if (report.data == null || report.data.rows == null || report.data.rows.isEmpty()) {
                        System.out.println("Report has empty data!");
                        continue;
                    }
                    final List<BqTableRow> data = processReport(report, reportName, view, schema);
                    System.out.println("Data has been processed");
                    createCsv(data, csvPath);
                    System.out.println("CSV saved: '" + csvPath + "'");
                } else {
                    System.out.println("CSV already exists: '" + csvPath + "'");
                }
            }
        }
        if (needBqUpload) {
            uploadData(view, schema, overrideBqTable);
        }
    }

    private static void uploadData(
            final String view,
            final String schema,
            final boolean overrideBqTable
    ) throws IOException, InterruptedException {
        final List<JobReference> jobReferenceList = Lists.newArrayList();
        final Bigquery bigQueryClient = getBqClient(CREDENTIAL_BQ_PATH);
        final JobReference jobReference = saveBq(bigQueryClient, view, schema, view + "_" + schema, overrideBqTable);
        System.out.println("Data has been saved in BQ table: '" + view + "_" + schema + "'");
        jobReferenceList.add(jobReference);
        final List<JobReference> errors = Lists.newArrayList();
        for (final JobReference jr : jobReferenceList) {
            while (true) {
                final JobStatus jobStatus = getJobStatus(bigQueryClient, jr);
                if (jobStatus.getErrors() != null && !jobStatus.getErrors().isEmpty()) {
                    errors.add(jr);
                } else if ("FAILURE".equals(jobStatus.getState())) {
                    errors.add(jr);
                } else if ("PENDING".equals(jobStatus.getState()) || "RUNNING".equals(jobStatus.getState())) {
                    Thread.sleep(10000L);
                } else if ("DONE".equals(jobStatus.getState()) || "SUCCESS".equals(jobStatus.getState())) {
                    System.out.println("Data has been uploaded in BQ table: '" + view + "_" + schema + "'");
                    break;
                } else {
                    errors.add(jr);
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new RuntimeException("Upload error! Uploads with error count: " + errors.size());
        }
    }

    private static List<BqTableRow> processReport(final ReportBq report, final String reportName, final String view, final String schema) {
        final List<BqTableRow> result = Lists.newArrayList();
        final List<String> dimensions = report.columnHeader.dimensions;
        if (dimensions.size() != 3) {
            throw new RuntimeException("Wrong dimension size!");
        }
        final List<ReportBq.MetricHeaderEntryBq> metrics = report.columnHeader.metricHeader.metricHeaderEntries;
        final List<ReportBq.RowBq> data = report.data.rows;
        for (final ReportBq.RowBq row : data) {
            final List<String> dimensionValues = row.dimensions;
            if (dimensionValues.size() != dimensions.size()) {
                throw new RuntimeException("Incorrect dimensions size!");
            }

            final List<String> metricValues = row.metrics.get(0).values;
            if (metricValues.size() != metrics.size()) {
                throw new RuntimeException("Incorrect metrics size!");
            }

            for (int metricCount = 0; metricCount < metrics.size(); metricCount++) {
                final BqTableRow bqTableRow = new BqTableRow()
                        .setReportPrefix(view + "::" + schema)
                        .setReportType(fillReportType(reportName))
                        .setReportSource(reportName);


                for (int dimCount = 0; dimCount < dimensions.size(); dimCount++) {
                    final String dimensionName = dimensions.get(dimCount);
                    final String dimensionValue = dimensionValues.get(dimCount);
                    if (DATE_DIMENSION.equals(dimensionName)) {
                        bqTableRow.setDate(dimensionValue);
                        continue;
                    }
                    if (bqTableRow.getDim1Name() == null) {
                        bqTableRow.setDim1Name(dimensionName)
                                .setDim1Value(dimensionValue);
                    } else {
                        bqTableRow.setDim2Name(dimensionName)
                                .setDim2Value(dimensionValue);
                    }
                }

                final ReportBq.MetricHeaderEntryBq metricHeaderEntry = metrics.get(metricCount);

                bqTableRow.setMetricName(metricHeaderEntry.name)
                        .setMetricType(metricHeaderEntry.type)
                        .setMetricValue(metricValues.get(metricCount));

                result.add(bqTableRow);
            }
        }
        return result;
    }

    private static String createCsv(final List<BqTableRow> data, final String csvPath) throws IOException {
        final Storage storage = getStorage(CREDENTIAL_BQ_PATH);
        final List<BqTableRow> filtered = data.stream().filter(it -> !(it.getMetricValue().equals("0.0") || it.getMetricValue().equals("0"))).toList();
        final String content = fillCsvContent(filtered);
        final StorageObject object = storage.objects().insert(
                BUCKET_NAME,
                new StorageObject()
                        .setBucket(BUCKET_NAME)
                        .setName(csvPath),
                new InputStreamContent("text/plain", new ByteArrayInputStream(content.getBytes()))
        ).execute();
        return object.getName();
    }

    private static String fillCsvContent(final List<BqTableRow> data) {
        final StringBuilder content = new StringBuilder();
        content.append("date").append(",");
        content.append("dim1Name").append(",");
        content.append("dim1Value").append(",");
        content.append("dim2Name").append(",");
        content.append("dim2Value").append(",");
        content.append("metricName").append(",");
        content.append("metricType").append(",");
        content.append("metricValue").append(",");
        content.append("reportPrefix").append(",");
        content.append("reportType").append(",");
        content.append("reportSource").append("\n");

        for (final BqTableRow row : data) {
            content.append("\"").append(escapeValue(row.getDate())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getDim1Name())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getDim1Value())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getDim2Name())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getDim2Value())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getMetricName())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getMetricType())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getMetricValue())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getReportPrefix())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getReportType())).append("\"").append(",");
            content.append("\"").append(escapeValue(row.getReportSource())).append("\"").append("\n");
        }

        return content.toString();
    }

    private static String escapeValue(final String value) {
        if (value.contains("\"")) {
            return value.replace("\"", "\"\"");
        }
        return value;
    }

    private static JobReference saveBq(final Bigquery bigQueryClient, final String view, final String schema, final String tableId, final boolean overrideBqTable) throws IOException {
        final String fullPath = "gs://" + BUCKET_NAME + "/" + view + "/" + schema + "/*" + CSV_POSTFIX;
        final String projectId = "dev-export-bq";
        final String datasetId =  "ga_ca_reports";

        final TableReference tableReference = new TableReference();
        tableReference.setProjectId(projectId);
        tableReference.setDatasetId(datasetId);
        tableReference.setTableId(tableId);

//        final TableSchema tableSchema = new TableSchema();
//        tableSchema.getFields().addAll(getFieldSchema());

        final JobConfigurationLoad jobConfigurationLoad = new JobConfigurationLoad()
                .setAllowJaggedRows(false)
                .setAllowQuotedNewlines(true)
                .setDestinationTable(tableReference)
                .setMaxBadRecords(0)
                .setSkipLeadingRows(1)
                .setWriteDisposition(overrideBqTable ? "WRITE_TRUNCATE" : "WRITE_APPEND")
                .setSourceFormat("CSV")
                .setSourceUris(ImmutableList.of(fullPath));
//                .setSchema(tableSchema);

        final JobConfiguration jobConfiguration = new JobConfiguration();
        jobConfiguration.setLoad(jobConfigurationLoad);

        final Bigquery.Jobs.Insert request = bigQueryClient.jobs().insert(
                projectId,
                new Job().setConfiguration(jobConfiguration)
        );

        final JobReference jobReference = request.execute().getJobReference();

        final JobStatus jobStatus = getJobStatus(bigQueryClient, jobReference);
        if (jobStatus.getErrors() != null && !jobStatus.getErrors().isEmpty()) {
            throw new RuntimeException("Upload error! CSV path: " + fullPath);
        } else if ("FAILURE".equals(jobStatus.getState())) {
            throw new RuntimeException("Upload error! CSV path: " + fullPath);
        } else if ("PENDING".equals(jobStatus.getState()) || "RUNNING".equals(jobStatus.getState())) {
            //do nothing
        } else if ("DONE".equals(jobStatus.getState()) || "SUCCESS".equals(jobStatus.getState())) {
            //do nothing
        } else {
            throw new RuntimeException("Upload error! CSV path: " + fullPath);
        }

        return jobReference;
    }

    public static JobStatus getJobStatus(final Bigquery bigQueryClient, final JobReference jobReference) throws IOException {
        Bigquery.Jobs.Get request = bigQueryClient.jobs().get(jobReference.getProjectId(), jobReference.getJobId());
        if (jobReference.getLocation() != null) {
            request = request.setLocation(jobReference.getLocation());
        }
        final Job job = request.setFields("configuration(labels,jobType),status").execute();
        if (job != null && job.getStatus() != null) {
            return job.getStatus();
        } else {
            throw new IOException("Cannot get jobStatus for: " + jobReference);
        }
    }

    private static String fillReportType(final String reportName) {
//        129100799/acquisition/campaign/all.json/ga:sourcemedium/2016-01-01__2016-12-31_null.json
        final String[] arr = reportName.split("/");
        return arr[arr.length - 4] + "::" + arr[arr.length - 3].replace(".json", "") + "::" + arr[arr.length - 2].replace("ga:", "");
    }

    private static Storage getStorage(final String credPath) {
        if (STORAGE_CLIENT == null) {
            STORAGE_CLIENT = new Storage.Builder(
                    getHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    getCredentials(credPath).createScoped(Arrays.asList(StorageScopes.CLOUD_PLATFORM))
            ).setApplicationName("Cloudaware").build();
        }
        return STORAGE_CLIENT;
    }

    private static Bigquery getBqClient(final String credPath) {
        if (BQ_CLIENT == null) {
            BQ_CLIENT = new Bigquery.Builder(
                    getHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    getCredentials(credPath).createScoped(Arrays.asList(StorageScopes.CLOUD_PLATFORM))
            ).setApplicationName("Cloudaware").build();
        }
        return BQ_CLIENT;
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

    private static GoogleCredential getCredentials(final String path) {
        if (CREDENTIALS == null) {
            try {
                final InputStream inputStream = GoogleAnalyticsBqLoader.class.getResourceAsStream(path);
                CREDENTIALS = GoogleCredential.fromStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return CREDENTIALS;
    }

    public static List<StorageObject> listBucketPrefix(final Storage client, final String bucket, final String path) throws IOException {
        Objects workingObjects;
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

    public static String getFileContent(final Storage client, final String bucket, final String path) throws IOException {
        final String content;
        try (InputStream inputStream = client.objects().get(bucket, path).executeMediaAsInputStream()) {
            content = CharStreams.toString(new InputStreamReader(inputStream));
        }
        return content;
    }

    private static class BqTableRow {
        private String date;
        private String dim1Name;
        private String dim1Value;
        private String dim2Name;
        private String dim2Value;
        private String metricName;
        private String metricType;
        private String metricValue;
        private String reportPrefix;
        private String reportType;
        private String reportSource;

        public BqTableRow() {
        }

        public BqTableRow(
                String date,
                String dim1Name,
                String dim1Value,
                String dim2Name,
                String dim2Value,
                String metricName,
                String metricType,
                String metricValue,
                String reportPrefix,
                String reportType,
                String reportSource
        ) {
            this.date = date;
            this.dim1Name = dim1Name;
            this.dim1Value = dim1Value;
            this.dim2Name = dim2Name;
            this.dim2Value = dim2Value;
            this.metricName = metricName;
            this.metricType = metricType;
            this.metricValue = metricValue;
            this.reportPrefix = reportPrefix;
            this.reportType = reportType;
            this.reportSource = reportSource;
        }

        public String getDate() {
            return date;
        }

        public BqTableRow setDate(String date) {
            this.date = date;
            return this;
        }

        public String getDim1Name() {
            return dim1Name;
        }

        public BqTableRow setDim1Name(String dim1Name) {
            this.dim1Name = dim1Name;
            return this;
        }

        public String getDim1Value() {
            return dim1Value;
        }

        public BqTableRow setDim1Value(String dim1Value) {
            this.dim1Value = dim1Value;
            return this;
        }

        public String getDim2Name() {
            return dim2Name;
        }

        public BqTableRow setDim2Name(String dim2Name) {
            this.dim2Name = dim2Name;
            return this;
        }

        public String getDim2Value() {
            return dim2Value;
        }

        public BqTableRow setDim2Value(String dim2Value) {
            this.dim2Value = dim2Value;
            return this;
        }

        public String getMetricName() {
            return metricName;
        }

        public BqTableRow setMetricName(String metricName) {
            this.metricName = metricName;
            return this;
        }

        public String getMetricType() {
            return metricType;
        }

        public BqTableRow setMetricType(String metricType) {
            this.metricType = metricType;
            return this;
        }

        public String getMetricValue() {
            return metricValue;
        }

        public BqTableRow setMetricValue(String metricValue) {
            this.metricValue = metricValue;
            return this;
        }

        public String getReportPrefix() {
            return reportPrefix;
        }

        public BqTableRow setReportPrefix(String reportPrefix) {
            this.reportPrefix = reportPrefix;
            return this;
        }

        public String getReportType() {
            return reportType;
        }

        public BqTableRow setReportType(String reportType) {
            this.reportType = reportType;
            return this;
        }

        public String getReportSource() {
            return reportSource;
        }

        public BqTableRow setReportSource(String reportSource) {
            this.reportSource = reportSource;
            return this;
        }
    }
}
