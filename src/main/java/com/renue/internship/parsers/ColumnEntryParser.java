package com.renue.internship.parsers;

import com.renue.internship.app.version_2.ColumnEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ColumnEntryParser implements Parser<ColumnEntry> {
    private final String filename;

    public ColumnEntryParser(String filename) {
        this.filename = filename;
    }

    public String parseLine(long charsBefore) {
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {
            file.seek(charsBefore);
            return file.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ColumnEntry> parseColumn(int columnIndex) {
        long offset = 0;
        List<ColumnEntry> column = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String currentLine = reader.readLine();
            String cell = getCell(columnIndex, currentLine);
            while (currentLine != null) {
                column.add(new ColumnEntry(cell, offset));

                long notOneByteCharactersCount = 0;
                for (int j = 0; j < currentLine.length(); j++) {
                    notOneByteCharactersCount += String.valueOf(currentLine.charAt(j)).getBytes().length - 1;
                }
                offset += currentLine.length() + notOneByteCharactersCount + 1;

                currentLine = reader.readLine();
                if (currentLine != null) {
                    cell = getCell(columnIndex, currentLine);
                }
            }
            return column;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCell(int columnIndex, String currentLine) {
        return currentLine.split(",")[columnIndex].replaceAll("\"", "").toLowerCase();
    }
}
