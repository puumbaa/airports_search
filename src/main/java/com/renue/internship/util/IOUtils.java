package com.renue.internship.util;

import com.renue.internship.common.ResultEntry;

import java.util.Collection;

public class IOUtils {
    private static int columnIndex;

    private IOUtils() {
    }

    public static int getColumnIndex() {
        return columnIndex;
    }

    public static int parseColumnIndex(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Не указан номер столбца. Проверьте аргменты запуска");
        }
        columnIndex = Integer.parseInt(args[0]) - 1;
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
