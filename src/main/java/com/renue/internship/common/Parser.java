package com.renue.internship.common;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class Parser<T> {
    protected final String filename;
    protected final URL resource;
    protected Parser(String filename) {
        this.filename = filename;
        resource = this.getClass().getClassLoader().getResource(filename);
    }

    public String parseLine(long charsBefore) {
        try (RandomAccessFile file = new RandomAccessFile(resource.getFile(), "r")) {
            file.seek(charsBefore);
            return new String(file.readLine().getBytes(ISO_8859_1), UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void parseColumn(int columnIndex, T destination);

    public static String getCell(int columnIndex, String currentLine) {
        return currentLine.split(",")[columnIndex].replaceAll("\"", "").toLowerCase();
    }
}
