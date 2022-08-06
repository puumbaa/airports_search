package com.renue.internship.app.version_2;


import com.renue.internship.parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {


    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Не указан номер столбца. Проверьте аргменты запуска");
        }
        int columnIndex = Integer.parseInt(args[0]) - 1;
        Scanner sc = new Scanner(System.in);
        Map<Integer, Long> charsBeforePerLine = new HashMap<>();
        Trie trie = new Trie();
        Parser parser = new Parser("src\\main\\resources\\airports.csv");
        boolean isNumberTypeColumn;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(com.renue.internship.app.version_1.Application.class.getClassLoader().getResourceAsStream("airports.csv"))))) {

            String currentLine = reader.readLine();
            isNumberTypeColumn = currentLine.split(",")[columnIndex].matches("[0-9]+");
            long offset = 0;
            for (int i = 0; currentLine != null; i++, currentLine = reader.readLine()) {
                String word = currentLine.split(",")[columnIndex].replaceAll("\"", "").toLowerCase();
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
                String query = sc.nextLine();
                if (query.equals("\"!quit")) {
                    break;
                }

                Set<Integer> hits = trie.hits(query);

                ConcurrentSkipListSet<ResultEntry> resultSet;
                if (isNumberTypeColumn) {
                    resultSet = new ConcurrentSkipListSet<>(new ResultEntry.NumberTypeComparator());
                } else {
                    resultSet = new ConcurrentSkipListSet<>();
                }

                StringBuilder result = new StringBuilder();
                ExecutorService executorService = Executors.newCachedThreadPool();
                for (Integer lineNumber : hits) {
                    executorService.submit(() -> {
                        String line = parser.parse(charsBeforePerLine.get(lineNumber));
                        resultSet.add(new ResultEntry(line.split(",")[columnIndex], line));
                    });
                }
                executorService.shutdown();
                //noinspection ResultOfMethodCallIgnored
                executorService.awaitTermination(10, TimeUnit.SECONDS);
                resultSet.forEach(result::append);
                System.out.println(result);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
