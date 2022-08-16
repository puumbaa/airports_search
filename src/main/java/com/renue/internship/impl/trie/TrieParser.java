package com.renue.internship.impl.trie;

import com.renue.internship.common.Parser;
import com.renue.internship.common.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class TrieParser extends Parser<Trie> {

    public TrieParser(String filename) {
        super(filename);
    }

    @Override
    public void parseColumn(int columnIndex, Trie destination) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileAbsolutePath))) {
            String currentLine = reader.readLine();
            Queue<String> column = new LinkedList<>();
            int offset = 0;
            while (currentLine != null){
                String word = Parser.getCell(columnIndex,currentLine,true);
                if (word.equals("\\n")) {
                    offset += currentLine.getBytes().length + System.lineSeparator().getBytes().length;
                    currentLine = reader.readLine();
                    continue;
                }
                destination.insert(word, offset);
                column.add(word);
                offset += currentLine.getBytes().length + System.lineSeparator().getBytes().length;
                currentLine = reader.readLine();
            }
            destination.setType(Type.get(column));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
