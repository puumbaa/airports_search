package com.renue.internship.impl.trie;


import com.renue.internship.common.AutoComplete;
import com.renue.internship.common.Parser;
import com.renue.internship.common.ResultEntry;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static com.renue.internship.common.ResultEntry.from;
import static com.renue.internship.util.IOUtils.print;


public class AutoCompleteTrieImpl implements AutoComplete {

    private final Parser<Trie> parser;
    private final Trie trie;


    public AutoCompleteTrieImpl(Parser<Trie> parser, int maxDepth) {
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
                .map(offset -> from(parser.parseLine(offset), columnIndex))
                .filter(resultEntry -> query.length() < trie.getDepth() || resultEntry.getWord().startsWith(query))
                .sorted(trie.getComparator())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
