package com.gigware.goldmine.pojo;

import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;

import java.util.List;

public class ReportBq {
    public ColumnHeaderBq columnHeader;
    public DataBq data;
    public String nextPageToken;

    public static class ColumnHeaderBq {
        public List<String> dimensions;
        public MetricHeaderBq metricHeader;
    }

    public static class DataBq {
        public List<RowBq> rows;
    }

    public static class MetricHeaderBq {
        public List<MetricHeaderEntryBq> metricHeaderEntries;
    }

    public static class MetricHeaderEntryBq {
        public String name;
        public String type;
    }

    public static class RowBq {
        public List<String> dimensions;
        public List<MetricBq> metrics;
    }

    public static class MetricBq {
        public List<String> values;
    }
}
