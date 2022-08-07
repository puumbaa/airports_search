package com.renue.internship.parsers;

import com.renue.internship.app.version_2.ColumnEntry;

import java.io.*;
import java.util.List;


public class ColumnEntryListParser extends Parser<List<ColumnEntry>> {

    public ColumnEntryListParser(String filename) {
        super(filename);
    }

    public void parseColumn(int columnIndex, List<ColumnEntry> column) {
        long offset = 0;

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
