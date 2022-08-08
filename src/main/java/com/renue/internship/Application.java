package com.renue.internship;

import com.renue.internship.common.AutoComplete;
import com.renue.internship.common.Parser;
import com.renue.internship.impl.binary_search.AutocompleteBinarySearchImpl;
import com.renue.internship.impl.binary_search.ColumnEntry;
import com.renue.internship.impl.binary_search.ColumnEntryListParser;
import com.renue.internship.impl.trie.AutoCompleteTrieImpl;
import com.renue.internship.impl.trie.Trie;
import com.renue.internship.impl.trie.TrieParser;

import java.util.List;
import java.util.Scanner;

import static com.renue.internship.util.IOUtils.getColumnIndex;

public class Application {
    private static final String FILE_NAME = "airports.csv";

    public static void main(String[] args) {

        System.out.println("Выберите реализацию:\n" +
                "1. Префиксное дерево\n" +
                "2. Бинарный поиск с кешированием");

        Scanner sc = new Scanner(System.in);
        String query = sc.next();
        while (!query.matches("[1-2]")) {
            System.err.println("Введенно некорректное значение. Повторите ввод");
            query = sc.next();
        }

        AutoComplete autoComplete;
        if (query.equals("1")) {
            Parser<Trie> parser = new TrieParser(FILE_NAME);
            autoComplete = new AutoCompleteTrieImpl(parser, 4);
        } else {
            Parser<List<ColumnEntry>> parser = new ColumnEntryListParser(FILE_NAME);
            autoComplete = new AutocompleteBinarySearchImpl(parser, true);
        }
        autoComplete.run(getColumnIndex(args));
    }
}
