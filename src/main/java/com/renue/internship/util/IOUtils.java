package com.renue.internship.util;

import com.renue.internship.common.ResultEntry;

import java.io.*;
import java.util.Collection;
import java.util.Objects;

public class IOUtils {

    private static int columnIndex;
    private static final int MAX_COLUMN_NUMBER = 14;
    private static final int MIN_COLUMN_NUMBER = 1;

    private static File tempFile;

    public static File getTempFile() {
        return tempFile;
    }

    public static void createTempFileFromResource(String resourceName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName))))) {

            File temp = File.createTempFile("temp", "airports.csv");
            temp.deleteOnExit();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
                String line = reader.readLine();
                while (line != null) {
                    writer.write(line + "\n");
                    line = reader.readLine();
                }
            }
            tempFile = temp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private IOUtils() {
    }

    public static int getColumnIndex() {
        return columnIndex;
    }

    public static int parseColumnIndex(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Не указан номер столбца. Проверьте аргменты запуска");
        }
        int column = Integer.parseInt(args[0]);
        columnIndex = column - 1;
        if (column < MIN_COLUMN_NUMBER || column > MAX_COLUMN_NUMBER) {
            throw new IllegalArgumentException(
                    String.format("Номер столбца должен быть от %d до %d", MIN_COLUMN_NUMBER, MAX_COLUMN_NUMBER)
            );
        }
        return columnIndex;
    }

    public static void print(Collection<ResultEntry> resultSet, long startTime) {
        resultSet.forEach(System.out::println);
        System.out.println(
                "Количество найденных строк: " + resultSet.size() +
                        " | Затраченное время на поиск: " + (System.currentTimeMillis() - startTime) + "мс\n"
        );
    }

}
