package com.renue.internship.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public abstract class Parser<T> {
    protected final String filename;
    protected final InputStream resource;

    protected Parser(String filename) {
        this.filename = filename;
        resource = this.getClass().getClassLoader().getResourceAsStream(filename);
    }

    public String parseLine(long charsBefore) {
        try (RandomAccessFile file = new RandomAccessFile("classes\\" + filename, "r")) {
            file.seek(charsBefore);
            return file.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void parseColumn(int columnIndex, T destination);

    public static String getCell(int columnIndex, String currentLine) {
        return currentLine.split(",")[columnIndex].replaceAll("\"", "").toLowerCase();
    }
}