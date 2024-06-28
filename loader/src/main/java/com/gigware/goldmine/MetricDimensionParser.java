package com.gigware.goldmine;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigware.goldmine.pojo.GoogleAnalyticItem;
import com.gigware.goldmine.pojo.GoogleAnalyticItems;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetricDimensionParser {
    public static final Map<String, Dimension> dimensions = Maps.newHashMap();
    public static final Map<String, Metric> metrics = Maps.newHashMap();

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final String METRIC_DIMENSION_JSON_PATH = "";

    public static void main(String[] args) {
        try {
            execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void execute() throws IOException {
        final File file = new File(METRIC_DIMENSION_JSON_PATH);
        final StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }

        final GoogleAnalyticItems gaItems = MAPPER.readValue(content.toString(), GoogleAnalyticItems.class);

        fillMetric(gaItems.items);
        fillDimension(gaItems.items);

        final List<String> metricNames = new ArrayList<>(metrics.keySet()).stream().sorted().collect(Collectors.toList());
        final List<String> dimensionNames = new ArrayList<>(dimensions.keySet()).stream().sorted().collect(Collectors.toList());

        System.out.println("METRICS:\n");
        for (final String metricName : metricNames) {
            Metric metric = metrics.get(metricName);
            System.out.println("    " + metricName
                    + "(\"" + metric.getExpression() + "\", "
                    + "\"" + metric.getAlias() + "\"),"
            );
        }

        System.out.println("\n");
        System.out.println("DIMENSIONS:\n");
        for (final String dimensionName : dimensionNames) {
            Dimension dimension = dimensions.get(dimensionName);
            System.out.println("    " + dimensionName
                    + "(\"" + dimension.getName() + "\"),"
            );
        }
    }

    private static void fillMetric(final List<GoogleAnalyticItem> items) {
        items.stream().filter(it ->
                        it.attributes.type != null && it.attributes.type.equals("METRIC")
                                && it.attributes.status != null && !it.attributes.status.equals("DEPRECATED")
                )
                .forEach(metric -> {
                    final String name = simplifyName(metric.attributes.group)
                            + "__"
                            + simplifyName(metric.attributes.uiName).replace("__", "_");
                    metrics.put(name, new Metric().setExpression(metric.id).setAlias(metric.id.split(":")[1]));
                });
    }

    private static void fillDimension(final List<GoogleAnalyticItem> items) {
        items.stream().filter(it ->
                        it.attributes.type != null && it.attributes.type.equals("DIMENSION")
                                && it.attributes.status != null && !it.attributes.status.equals("DEPRECATED")
                )
                .forEach(metric -> {
                    final String name = simplifyName(metric.attributes.group)
                            + "__"
                            + simplifyName(metric.attributes.uiName).replace("__", "_");
                    dimensions.put(name, new Dimension().setName(metric.id));
                });
    }

    private static String simplifyName(final String str) {
        return str.replace(".", "")
                .replace("/", "PER")
                .replace("%", "PERCENT")
                .replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace(":", "")
                .replace(" ", "_")
                .toUpperCase();
    }

}
