package com.renue.internship.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing column number");
        }
        int columnIndex = Integer.parseInt(args[0]) - 1;
        Scanner sc = new Scanner(System.in);
        Map<String, String> result = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Application.class.getClassLoader().getResourceAsStream("airports.csv")))) {
            while (true) {
                System.out.println("Введите строку: ");
                String query = "\"" + sc.nextLine();
                long start = System.currentTimeMillis();
                if (query.equals("\"!quit")) {
                    break;
                }
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    String columnValue = currentLine.split(",")[columnIndex];
                    if (columnValue.startsWith(query)) {
                        result.put(columnValue, currentLine);
                    }
                }
                result.forEach((s, s2) -> {
                    System.out.println(s + "[" + s2 + "]");
                });
                long time = System.currentTimeMillis() - start;
                System.out.printf("Количество найденных строк: %s | Затраченное время на поиск: %s мс", result.size(), time);
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
