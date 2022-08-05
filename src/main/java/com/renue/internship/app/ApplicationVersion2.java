package com.renue.internship.app;


import com.renue.internship.app.old.ApplicationVersion1;
import com.renue.internship.parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ApplicationVersion2 {


    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing column number");
        }
        int columnIndex = Integer.parseInt(args[0]) - 1;
        Scanner sc = new Scanner(System.in);
        Map<Integer, Long> charsBeforePerLine = new HashMap<>();
        Trie trie = new Trie();
        Parser parser = new Parser("src\\main\\resources\\airports.csv");


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(ApplicationVersion1.class.getClassLoader().getResourceAsStream("airports.csv"))))) {

            String currentLine;
            long offset = 0;
            for (int i = 0; (currentLine = reader.readLine()) != null; i++) {
                String word = currentLine.split(",")[columnIndex];
                charsBeforePerLine.put(i, offset);
                trie.insert(word, i);
                long notOneByteCharactersCount = 0;
                for (int j = 0; j < currentLine.length(); j++) {
                    notOneByteCharactersCount += String.valueOf(currentLine.charAt(j)).getBytes().length - 1;
                }
                offset += currentLine.length() + notOneByteCharactersCount + 1;
            }

            while (true) {
                System.out.println("Введите строку: ");
                String query = "\"" + sc.nextLine();
                if (query.equals("\"!quit")) {
                    break;
                }
                long start = System.currentTimeMillis();
                Set<Integer> hits = trie.hits(query);
                for (Integer lineNumber : hits) {
                    String parsed = parser.parse(charsBeforePerLine.get(lineNumber));
                    System.out.println(parsed.split(",")[columnIndex] + "[" + parsed + "]");
                }
                System.out.printf("Количество найденных строк: %s | Затраченное время на поиск: %s мс", hits.size(),
                        System.currentTimeMillis() - start);
                System.out.println();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
