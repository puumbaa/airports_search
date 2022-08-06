package com.renue.internship.app.version_3;

import com.renue.internship.parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Не указан номер столбца. Проверьте аргменты запуска");
        }

        int columnIndex = Integer.parseInt(args[0]) - 1;
        Parser parser = new Parser("src\\main\\resources\\airports.csv");
        Scanner sc = new Scanner(System.in);

        List<KeywordEntry> keywordEntries = new ArrayList<>();
        boolean isNumberTypeColumn = parse(columnIndex, keywordEntries);

        keywordEntries.sort(
                isNumberTypeColumn ?
                        Comparator.comparingInt(o -> Integer.parseInt(o.getKeyword())) :
                        Comparator.comparing(KeywordEntry::getKeyword)
        );


        while (true) {
            int startIndex = 0;
            int endIndex = keywordEntries.size() - 1;

            System.out.println("Введите строку: ");
            String query = sc.nextLine().toLowerCase();
            if (query.equals("\"!quit")) {
                break;
            }
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < query.length(); i++) {
                while (startIndex <= endIndex) {
                    int target = startIndex + ((endIndex - startIndex) / 2);
                    String keyword = keywordEntries.get(target).getKeyword();
                    if (keyword.length() <= i || keyword.charAt(i) < query.charAt(i) && keyword.startsWith(query.substring(0,i))) {
                        startIndex = target + 1;
                    } else if (keyword.charAt(i) > query.charAt(i) || !keyword.startsWith(query.substring(0,i))) {
                        endIndex = target - 1;
                    } else {
                        break;
                    }
                }
            }
            int count = 0;
            if (startIndex <= endIndex) {
                for (int i = startIndex; i < endIndex; i++) {
                    KeywordEntry item = keywordEntries.get(i);
                    if (item.getKeyword().startsWith(query)) {
                        count++;
                        System.out.println(item.getKeyword() + "[" + parser.parse(item.getCharsBefore()) + "]");
                    }
                }
            }
            System.out.printf(
                    "Количество найденных строк: %s | Затраченное время на поиск: %s мс",
                    count, System.currentTimeMillis() - startTime
            );
            System.out.println();
        }
    }

    private static boolean parse(int columnIndex, List<KeywordEntry> keywordEntries) {
        boolean isNumberTypeColumn;
        long offset = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Application.class.getClassLoader().getResourceAsStream("airports.csv"))))) {
            String currentLine = reader.readLine();
            String keyword = currentLine.split(",")[columnIndex].replaceAll("\"","").toLowerCase();
            isNumberTypeColumn = keyword.matches("[0-9]+");
            while (currentLine != null) {
                keywordEntries.add(new KeywordEntry(keyword, offset));

                long notOneByteCharactersCount = 0;
                for (int j = 0; j < currentLine.length(); j++) {
                    notOneByteCharactersCount += String.valueOf(currentLine.charAt(j)).getBytes().length - 1;
                }
                offset += currentLine.length() + notOneByteCharactersCount + 1;

                currentLine = reader.readLine();
                if (currentLine != null){
                    keyword = currentLine.split(",")[columnIndex].replaceAll("\"","").toLowerCase();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return isNumberTypeColumn;
    }
}
