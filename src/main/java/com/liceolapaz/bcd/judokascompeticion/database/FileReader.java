package com.liceolapaz.bcd.judokascompeticion.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;


public class FileReader {
    public static List<String> readToList(String url) throws IOException {
        Path path = Paths.get(url);

        List<String> lines = Files.readAllLines(path);

        return lines;
    }
}
