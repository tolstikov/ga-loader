package com.gigware.goldmine.pojo;

import java.util.List;
import java.util.Optional;

public final class ProcessLog {
    private List<ProcessLogRecord> records;

    public ProcessLog() {
    }

    public ProcessLog(final List<ProcessLogRecord> records) {
        this.records = records;
    }

    public List<ProcessLogRecord> getRecords() {
        return records;
    }

    public ProcessLog setRecords(final List<ProcessLogRecord> records) {
        this.records = records;
        return this;
    }

    public Optional<ProcessLogRecord> findReportLog(final AnalyticsReport report) {
        return this.records == null ? Optional.empty() : this.records.stream().filter(r -> r.getReportUniqueName().equals(report.getUniqueName())).findFirst();
    }
}
