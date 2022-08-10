package com.renue.internship.impl.trie;

import com.renue.internship.common.Parser;
import com.renue.internship.common.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrieParser extends Parser<Trie> {

    public TrieParser(String filename) {
        super(filename);
    }

    @Override
    public void parseColumn(int columnIndex, Trie destination) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileAbsolutePath))) {
            String currentLine = reader.readLine();
            destination.setType(Type.get(getCell(columnIndex, currentLine, true)));
            int offset = 0;
            while (currentLine != null){
                String word = Parser.getCell(columnIndex,currentLine,true);
                destination.insert(word, offset);

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
