package com.gigware.goldmine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Joiner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ReportsConverter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(final String[] args) throws URISyntaxException, IOException {
        ClassLoader classLoader = ReportsConverter.class.getClassLoader();
        URL resource = classLoader.getResource("reports"); // todo check if all
        List<File> reports = Files.walk(Paths.get(resource.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        for (final File f: reports) {
            final String json = readFile(f);
            System.out.println("Processing: " + f.getAbsolutePath());
            final JsonNode tree = MAPPER.readTree(json);
            JsonNode components = tree.get("components");
            for (JsonNode component : components) {
                List<String> keys = new ArrayList<>();
                component.fieldNames().forEachRemaining(keys::add);
                System.out.println("  " + Joiner.on(',').join(keys));
            }
            if (components == null) {
                System.out.println("Components not found");
            }
            continue;
//            if (components.isArray()) {
//                JsonNode table = null;
//                for (JsonNode e : components) {
//                    if(e.get("id").asText().equalsIgnoreCase("explorer-table")) {
//                        table = e;
//                        break;
//                    }
//                }
//                if (table == null) {
//                    System.out.println("Table not found");
//                    continue;
//                }
//                JsonNode dataTable = table.get("dataTable");
//                JsonNode dimensions = dataTable.get("dimension");
//                JsonNode metrics = dataTable.get("metric");
//                JsonNode tableControl = table.get("tableControl");
//                JsonNode dimensionPicker = tableControl.get("dimensionPicker");
//                JsonNode dimensionPickerGroup = dimensionPicker.get("group");
//                List<String> commonlyUsedDims = List.of();
//                for (JsonNode e : dimensionPickerGroup) {
//                    if (e.get("label").asText().equalsIgnoreCase("Commonly used")) {
//                        for (JsonNode d : e.get("conceptRef")) {
//                            commonlyUsedDims.add(d.asText());
//                        }
//                        break;
//                    }
//                }
//                if (commonlyUsedDims.isEmpty()) {
//                    System.out.println("Commonly used dimensions not found");
//                    continue;
//                }
//            } else {
//                System.out.println("Components node is NOT ARRAY");
//            }
            // components.[?id = "id": "explorer-table"].
            // dataTable.dimension + dataTable.metric and tableControl.dimensionPicker
//            System.out.println();
        }
//        collect.forEach(f -> System.out.println(f.getAbsolutePath()));
        System.out.println();
    }

    private static String readFile(final File file) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
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
}
