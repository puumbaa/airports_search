package com.renue.internship.app.common;

import com.renue.internship.app.version_1.AutoCompleteTrieImpl;
import com.renue.internship.app.version_1.Trie;
import com.renue.internship.parsers.Parser;
import com.renue.internship.parsers.TrieParser;

import static com.renue.internship.utils.ApplicationUtils.getColumnIndex;

public class Application {
    public static void main(String[] args) {
        Parser<Trie> parser = new TrieParser("src\\main\\resources\\airports.csv");
        AutoComplete solver = new AutoCompleteTrieImpl(parser, 4);
        solver.run(getColumnIndex(args));
    }
}
