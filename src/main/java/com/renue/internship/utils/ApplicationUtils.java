package com.renue.internship.utils;

import com.renue.internship.app.version_2.ResultSet;

public class ApplicationUtils {
    private ApplicationUtils(){
    }
    public static int getColumnIndex(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Не указан номер столбца. Проверьте аргменты запуска");
        }
        return Integer.parseInt(args[0]) - 1;
    }
    public static void print(ResultSet resultSet, long startTime) {
        System.out.println(
                resultSet.getResult() +
                        "Количество найденных строк: " + resultSet.getQuantity() +
                        " | Затраченное время на поиск: " + (System.currentTimeMillis() - startTime) + "мс\n"
        );
    }
}
