package com.renue.internship.util;

import com.renue.internship.common.ResultEntry;

import java.util.Collection;

public class IOUtils {

    private static int columnIndex;
    private static final int MAX_COLUMN_NUMBER = 14;
    private static final int MIN_COLUMN_NUMBER = 1;

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
        StringBuilder result = new StringBuilder();
        resultSet.forEach(result::append);
        System.out.println(result);
        System.out.println(
                "Количество найденных строк: " + resultSet.size() +
                        " | Затраченное время на поиск: " + (System.currentTimeMillis() - startTime) + "мс\n"
        );
    }

}
