package com.gigware.goldmine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigware.goldmine.pojo.AnalyticsReport;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.Metric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.StreamSupport;

public final class ReportsConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static String readFile(final File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    public List<AnalyticsReport> readReports() {

        try {
            ClassLoader classLoader = ReportsConverter.class.getClassLoader();
            URL resource = classLoader.getResource("gutted"); // todo check if all
            assert resource != null;
            return Files.walk(Paths.get(resource.toURI()))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(f -> f.getName().endsWith(".json"))
                    .map(this::readReport)
                    .toList();

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public AnalyticsReport readReport(final File file) {
        try {
            final JsonNode jsonNode = MAPPER.readTree(file);
            return new AnalyticsReport(
                    file.getName(),
                    file.getName(),
                    StreamSupport
                            .stream(jsonNode.withArrayProperty("dimension").spliterator(), false)
                            .map(jn -> new Dimension().setName(jn.get("externalName").asText()))
                            .toList(),
                    StreamSupport
                            .stream(jsonNode.withArrayProperty("metric").spliterator(), false)
                            .map(jn -> new Metric().setExpression(jn.get("externalName").asText()))
                            .toList()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
