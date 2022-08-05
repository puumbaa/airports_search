package com.renue.internship.parsers;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Parser {
    private final String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public String parse(long charsBefore) {
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            file.seek(charsBefore);
            return file.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
