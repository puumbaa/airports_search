package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class ColumnEntryListParser extends Parser<List<ColumnEntry>> {

    public ColumnEntryListParser(String filename) {
        super(filename);
    }

    public void parseColumn(int columnIndex, List<ColumnEntry> column) {
        long offset = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
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
