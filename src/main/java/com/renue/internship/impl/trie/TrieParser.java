package com.renue.internship.impl.trie;

import com.renue.internship.common.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrieParser extends Parser<Trie> {

    public TrieParser(String filename) {
        super(filename);
    }

    @Override
    public void parseColumn(int columnIndex, Trie destination) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {

            String currentLine = reader.readLine();
            destination.setNumberTypeTrie(currentLine.split(",")[columnIndex].matches("[0-9]+"));
            int offset = 0;
            for (int i = 0; currentLine != null; i++, currentLine = reader.readLine()) {
                String word = currentLine.split(",")[columnIndex].replaceAll("\"", "").toLowerCase();
                destination.insert(word, offset);
                long notOneByteCharactersCount = 0;
                for (int j = 0; j < currentLine.length(); j++) {
                    notOneByteCharactersCount += String.valueOf(currentLine.charAt(j)).getBytes().length - 1;
                }
                offset += currentLine.length() + notOneByteCharactersCount + 1;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
