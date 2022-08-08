package com.renue.internship.common;

import java.util.Comparator;

public class ResultEntry implements Comparable<ResultEntry>{
    public String getWord() {
        return word;
    }

    private final String word;
    private final String line;

    public ResultEntry(String word, String line) {
        this.word = word;
        this.line = line;
    }

    @Override
    public String toString() {
        return word + "[" + line + "]\n";
    }

    public String getLine() {
        return line;
    }

    @Override
    public int compareTo(ResultEntry o) {
        return word.compareTo(o.word);
    }

    public static class NumberTypeComparator implements Comparator<ResultEntry> {
        @Override
        public int compare(ResultEntry o1, ResultEntry o2) {
            return Integer.parseInt(o1.word) - Integer.parseInt(o2.word);
        }
    }

    public static class StringTypeComparator implements Comparator<ResultEntry>{

        @Override
        public int compare(ResultEntry o1, ResultEntry o2) {
            return o1.word.compareTo(o2.word);
        }
    }
}
