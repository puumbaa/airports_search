package com.renue.internship.app.version_2;

import com.renue.internship.app.AutoComplete;
import com.renue.internship.parsers.ColumnEntryParser;

import java.util.*;

import static com.renue.internship.utils.ApplicationUtils.print;

public class AutocompleteBinarySearchImpl implements AutoComplete {

    private final ColumnEntryParser parser;
    private final List<ColumnEntry> keywordEntries;


    public AutocompleteBinarySearchImpl(ColumnEntryParser parser, int columnIndex) {
        if (parser == null) {
            throw new IllegalStateException("Парсер должен быть задан");
        }
        this.parser = parser;
        this.keywordEntries = parser.parseColumn(columnIndex);
    }



    public void run() {
        if (!keywordEntries.isEmpty() && keywordEntries.get(0).getCell().matches("[0-9]+")) {
            keywordEntries.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getCell())));
        } else {
            keywordEntries.sort(Comparator.comparing(ColumnEntry::getCell));
        }
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Введите строку: ");
            String query = sc.nextLine().toLowerCase();
            if (query.equals("\"!quit")) {
                break;
            }
            long startTime = System.currentTimeMillis();
            PointerCouple pointerCouple = reduceSearchLimits(query, keywordEntries);
            ResultSet resultSet = findMatches(pointerCouple, query);
            print(resultSet, startTime);
        }
    }


    private PointerCouple reduceSearchLimits(String query, List<ColumnEntry> keywordEntries) {
        int startIndex = 0;
        int endIndex = keywordEntries.size() - 1;

        for (int i = 0; i < query.length(); i++) {
            while (startIndex <= endIndex) {
                int target = startIndex + ((endIndex - startIndex) / 2);
                String keyword = keywordEntries.get(target).getCell();
                boolean haveSamePrefixes = keyword.startsWith(query.substring(0, i));
                if (keyword.length() <= i || keyword.charAt(i) < query.charAt(i) && haveSamePrefixes) {
                    startIndex = target + 1;
                } else if (keyword.charAt(i) > query.charAt(i) && haveSamePrefixes) {
                    endIndex = target - 1;
                } else {
                    break;
                }
            }
        }
        return new PointerCouple(startIndex, endIndex);
    }

    private ResultSet findMatches(PointerCouple pointerCouple, String query) {
        if (pointerCouple.getStart() > pointerCouple.getEnd()) {
            return ResultSet.empty();
        }
        ResultSet resultSet = new ResultSet();
        for (int i = pointerCouple.getStart(); i < pointerCouple.getEnd(); i++) {
            ColumnEntry columnEntry = keywordEntries.get(i);
            if (columnEntry.getCell().startsWith(query)) {
                columnEntry.setRow(parser.parseLine(columnEntry.getBytesBeforeRow()));
                resultSet.add(columnEntry);
            }
        }
        return resultSet;
    }

}
