package com.gigware.goldmine.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class AnalyticsReport {

    private String label;
    private String uniqueName;
    private List<Dimension> dimensions;
    private List<Metric> metrics;
    private List<Dimension> secondaryDimensions;

    public AnalyticsReport(
            final String label,
            final String uniqueName,
            final List<Dimension> dimensions,
            final List<Metric> metrics
    ) {
        this(
                label,
                uniqueName,
                dimensions,
                metrics,
                List.of()
        );
    }

    public AnalyticsReport(
            final String label,
            final String uniqueName,
            final List<Dimension> dimensions,
            final List<Metric> metrics,
            final List<Dimension> secondaryDimensions
    ) {
        this.label = label;
        this.uniqueName = uniqueName;
        this.dimensions = dimensions;
        this.metrics = metrics;
        this.secondaryDimensions = secondaryDimensions;
    }

    public String getLabel() {
        return label;
    }

    public AnalyticsReport setLabel(final String label) {
        this.label = label;
        return this;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public AnalyticsReport setUniqueName(final String uniqueName) {
        this.uniqueName = uniqueName;
        return this;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public AnalyticsReport setDimensions(final List<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public AnalyticsReport setMetrics(final List<Metric> metrics) {
        this.metrics = metrics;
        return this;
    }

    public List<Dimension> getSecondaryDimensions() {
        return secondaryDimensions;
    }

    public AnalyticsReport setSecondaryDimensions(final List<Dimension> secondaryDimensions) {
        this.secondaryDimensions = secondaryDimensions;
        return this;
    }
}
