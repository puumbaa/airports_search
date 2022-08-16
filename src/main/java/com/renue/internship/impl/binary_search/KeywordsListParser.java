package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;
import com.renue.internship.common.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;


public class KeywordsListParser extends Parser<KeywordsList> {

    public KeywordsListParser(String filename) {
        super(filename);
    }

    public void parseColumn(int columnIndex, KeywordsList list) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName))))) {

            String currentLine = reader.readLine();
            Queue<String> column = new LinkedList<>();
            long offset = 0;
            while (currentLine != null) {
                String word = getCell(columnIndex, currentLine, true);
                if (word.equals("\\n")) {
                    offset += currentLine.getBytes().length + System.lineSeparator().getBytes().length;
                    currentLine = reader.readLine();
                    continue;
                }
                list.add(new KeywordsList.KeywordEntry(word, offset));
                column.add(word);
                offset += currentLine.getBytes().length + System.lineSeparator().getBytes().length;
                currentLine = reader.readLine();
            }
            list.setType(Type.get(column));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
