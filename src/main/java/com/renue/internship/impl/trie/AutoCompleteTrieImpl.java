package com.renue.internship.impl.trie;


import com.renue.internship.common.AutoComplete;
import com.renue.internship.common.Parser;
import com.renue.internship.common.ResultEntry;

import java.util.*;
import java.util.stream.Collectors;

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
        parse(columnIndex);
        start(columnIndex);
    }

    private void start(int columnIndex) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите строку: ");
            String query = sc.nextLine().toLowerCase();
            if (query.equals("!quit")) {
                break;
            }
            long start = System.currentTimeMillis();
            Set<Integer> hits = trie.hits(query);
            Queue<ResultEntry> resultSet = getResultSet(hits, query, columnIndex);
            print(resultSet, start);
        }
    }

    private void parse(int columnIndex) {
        parser.parseColumn(columnIndex, trie);
    }

    private Queue<ResultEntry> getResultSet(Set<Integer> hits, String query, int columnIndex) {
        return hits.stream()
                .parallel()
                .map(offset -> {
                    String line = parser.parseLine(offset);
                    String word = line.split(",")[columnIndex];
                    return new ResultEntry(word, line);
                })
                .filter(resultEntry ->
                        query.length() < trie.getMaxDepth() ||
                                resultEntry.getWord().startsWith(query))
                .sorted(trie.isNumberTypeTrie() ?
                        new ResultEntry.NumberTypeComparator() :
                        new ResultEntry.StringTypeComparator())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
