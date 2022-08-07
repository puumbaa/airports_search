package com.renue.internship.app.version_2;

import com.renue.internship.app.common.AutoComplete;
import com.renue.internship.app.common.ResultEntry;
import com.renue.internship.parsers.Parser;

import java.util.*;

import static com.renue.internship.utils.ApplicationUtils.print;

public class AutocompleteBinarySearchImpl implements AutoComplete {

    private final Parser<List<ColumnEntry>> parser;
    private final List<ColumnEntry> keywordEntries;


    public AutocompleteBinarySearchImpl(Parser<List<ColumnEntry>> parser) {
        if (parser == null) {
            throw new IllegalStateException("Парсер должен быть задан");
        }
        this.parser = parser;
        this.keywordEntries = new ArrayList<>();
    }


    public void run(int columnIndex) {
        parser.parseColumn(columnIndex, keywordEntries);

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
            List<ResultEntry> resultSet = findMatches(pointerCouple, query);
            print(resultSet, startTime);
        }
        keywordEntries.clear();
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

    private List<ResultEntry> findMatches(PointerCouple pointerCouple, String query) {
        if (pointerCouple.getStart() > pointerCouple.getEnd()) {
            return Collections.emptyList();
        }
        List<ResultEntry> resultEntries = new ArrayList<>();
        for (int i = pointerCouple.getStart(); i < pointerCouple.getEnd(); i++) {
            ColumnEntry columnEntry = keywordEntries.get(i);
            if (columnEntry.getCell().startsWith(query)) {
                resultEntries.add(new ResultEntry(columnEntry.getCell(),parser.parseLine(columnEntry.getBytesBeforeRow())));
            }
        }
        return resultEntries;
    }

    private static class PointerCouple {
        private final int start;
        private final int end;

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public PointerCouple(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }


}
