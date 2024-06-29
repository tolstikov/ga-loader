package com.gigware.goldmine;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public final class ReportsConverter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(final String[] args) throws URISyntaxException, IOException {
        ClassLoader classLoader = ReportsConverter.class.getClassLoader();
        URL resource = classLoader.getResource("reports"); // todo check if all
        List<File> collect = Files.walk(Paths.get(resource.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        collect.forEach(f -> System.out.println(f.getAbsolutePath()));
        System.out.println();
    }
}
