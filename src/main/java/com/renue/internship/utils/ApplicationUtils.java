package com.renue.internship.utils;

import com.renue.internship.app.common.ResultEntry;

import java.util.Collection;

public class ApplicationUtils {
    private ApplicationUtils(){
    }
    public static int getColumnIndex(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("Не указан номер столбца. Проверьте аргменты запуска");
        }
        return Integer.parseInt(args[0]) - 1;
    }

    public static void print(Collection<ResultEntry> resultSet, long startTime){
        StringBuilder result = new StringBuilder();
        resultSet.forEach(result::append);
                System.out.println(result);
                System.out.println(
                        "Количество найденных строк: " + resultSet.size() +
                        " | Затраченное время на поиск: " + (System.currentTimeMillis() - startTime) + "мс\n");
    }

}
