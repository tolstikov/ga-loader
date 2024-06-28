package com.gigware.goldmine.pojo;

import java.util.List;

public final class ProcessLogRecord {
    private String reportUniqueName;
    private List<String> processedDays;

    public ProcessLogRecord() {
    }

    public ProcessLogRecord(final String reportUniqueName, final List<String> processedDays) {
        this.reportUniqueName = reportUniqueName;
        this.processedDays = processedDays;
    }

    public String getReportUniqueName() {
        return reportUniqueName;
    }

    public ProcessLogRecord setReportUniqueName(final String reportUniqueName) {
        this.reportUniqueName = reportUniqueName;
        return this;
    }

    public List<String> getProcessedDays() {
        return processedDays;
    }

    public ProcessLogRecord setProcessedDays(final List<String> processedDays) {
        this.processedDays = processedDays;
        return this;
    }
}
