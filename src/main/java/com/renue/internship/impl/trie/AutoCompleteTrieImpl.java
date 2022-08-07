package com.renue.internship.impl.trie;


import com.renue.internship.common.AutoComplete;
import com.renue.internship.common.ResultEntry;
import com.renue.internship.common.Parser;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.renue.internship.util.IOUtils.print;


public class AutoCompleteTrieImpl implements AutoComplete {

    private final Parser<Trie> parser;
    private final Trie trie;

    public AutoCompleteTrieImpl(Parser<Trie> parser, int maxDepth) {
        if (maxDepth < 1 || maxDepth > 4) {
            throw new IllegalArgumentException("Максимальная глубина должна быть в промежутке от 1 до 4");
        }
        if (parser == null) {
            throw new IllegalStateException("Парсер должен быть задан");
        }
        this.parser = parser;
        this.trie = new Trie(maxDepth);
    }

    @Override
    public void run(int columnIndex) {
        parser.parseColumn(columnIndex, trie);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Введите строку: ");
            String query = sc.nextLine().toLowerCase();
            if (query.equals("!quit")) {
                break;
            }
            long start = System.currentTimeMillis();
            Set<Integer> hits = trie.hits(query);
            ConcurrentSkipListSet<ResultEntry> resultSet = getResultSet(hits, query, columnIndex);
            print(resultSet,start);
        }

    }

    private ConcurrentSkipListSet<ResultEntry> getResultSet(Set<Integer> hits, String query, int columnIndex) {
        ConcurrentSkipListSet<ResultEntry> resultSet;
        if (trie.isNumberTypeTrie()) {
            resultSet = new ConcurrentSkipListSet<>(new ResultEntry.NumberTypeComparator());
        } else {
            resultSet = new ConcurrentSkipListSet<>();
        }
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();

            for (Integer offset : hits) {
                executorService.submit(() -> {
                    String line = parser.parseLine(offset);
                    String word = line.split(",")[columnIndex];
                    if (query.length() <= trie.getMaxDepth() || word.startsWith(query)) {
                        resultSet.add(new ResultEntry(word, line));
                    }
                });
            }
            executorService.shutdown();
            //noinspection ResultOfMethodCallIgnored
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return resultSet;
    }
}
