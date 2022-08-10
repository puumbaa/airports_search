package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;
import com.renue.internship.common.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class KeywordsListParser extends Parser<KeywordsList> {

    public KeywordsListParser(String filename) {
        super(filename);
    }

    public void parseColumn(int columnIndex, KeywordsList list) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileAbsolutePath))) {
            String currentLine = reader.readLine();
            list.setType(Type.get(getCell(columnIndex, currentLine, true)));
            long offset = 0;
            while (currentLine != null) {
                String word = getCell(columnIndex, currentLine, true);
                list.add(new KeywordsList.KeywordEntry(word, offset));

                long notOneByteCharactersCount = 0;
                for (int j = 0; j < currentLine.length(); j++) {
                    notOneByteCharactersCount += String.valueOf(currentLine.charAt(j)).getBytes().length - 1;
                }
                offset += currentLine.length() + notOneByteCharactersCount + 1;
                currentLine = reader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
