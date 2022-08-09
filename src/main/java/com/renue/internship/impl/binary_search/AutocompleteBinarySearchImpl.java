package com.renue.internship.impl.binary_search;

import com.renue.internship.common.AutoComplete;
import com.renue.internship.common.Parser;
import com.renue.internship.common.ResultEntry;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.renue.internship.util.IOUtils.getColumnIndex;
import static com.renue.internship.util.IOUtils.print;

public class AutocompleteBinarySearchImpl implements AutoComplete {

    private final Parser<KeywordsList> parser;

    private final Map<String, List<ResultEntry>> cache;

    private final KeywordsList keywordsList;
    private final boolean useCache;

    private static final int CACHE_MAX_SIZE = 10;

    private List<ResultEntry> resultSet;

    public AutocompleteBinarySearchImpl(Parser<KeywordsList> parser, boolean useCache) {
        if (parser == null) {
            throw new IllegalStateException("Парсер должен быть задан");
        }
        this.useCache = useCache;
        this.parser = parser;
        this.keywordsList = new KeywordsList(new ArrayList<>());
        cache = useCache ? new HashMap<>() : null;
    }

    public AutocompleteBinarySearchImpl(Parser<KeywordsList> parser, KeywordsList keywordsList, boolean useCache) {
        if (parser == null) {
            throw new IllegalStateException("Парсер должен быть задан");
        }
        this.useCache = useCache;
        this.parser = parser;
        this.keywordsList = keywordsList;
        cache = useCache ? new HashMap<>() : null;
    }

    public void run(int columnIndex) {
        parse(columnIndex);
        keywordsList.sort();
        start();
    }

    private void parse(int columnIndex) {
        parser.parseColumn(columnIndex, keywordsList);
    }

    private void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите строку: ");
            String query = sc.nextLine().toLowerCase();
            if (query.equals("!quit")) {
                break;
            }
            long startTime = System.currentTimeMillis();
            if (useCache) {
                resultSet = searchInCache(query);
            } else {
                resultSet = getResultSet(query);
            }
            print(resultSet, startTime);
            if (useCache && cache.size() > CACHE_MAX_SIZE) {
                cache.clear();
            }
        }
    }

    private List<ResultEntry> getResultSet(String query) {
        PointerCouple pointerCouple = reduceSearchLimits(query, keywordsList);
        return findMatches(pointerCouple, query);
    }

    private List<ResultEntry> searchInCache(String query) {
        resultSet = cache.get(query);
        if (resultSet == null) {
            resultSet = getResultSet(query);
        }
        return resultSet;
    }



    private PointerCouple reduceSearchLimits(String query, KeywordsList keywordsList) {
        List<KeywordsList.KeywordEntry> keywords = keywordsList.getKeywords();
        int startIndex = 0;
        int endIndex = keywords.size() - 1;

        for (int i = 0; i < query.length(); i++) {
            while (startIndex <= endIndex) {
                int target = startIndex + ((endIndex - startIndex) / 2);
                String keyword = keywords.get(target).getCell();
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
        List<ResultEntry> resultEntries = IntStream.range(pointerCouple.getStart(), pointerCouple.getEnd() + 1)
                .parallel()
                .mapToObj(keywordsList.getKeywords()::get)
                .filter(columnEntry -> columnEntry.getCell().startsWith(query))
                .map(columnEntry -> {
                    String line = parser.parseLine(columnEntry.getBytesBeforeRow());
                    String word = line.split(",")[getColumnIndex()];
                    return new ResultEntry(word, line);
                })
                .collect(Collectors.toCollection(LinkedList::new));

        if (useCache) {
            cache.put(query, resultEntries);
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
