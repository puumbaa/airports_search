package com.renue.internship.common;

import java.util.Objects;

public class ResultEntry {
    public String getWord() {
        return word;
    }
    public String getNormalizedWord(){
        return word.replaceAll("\"","").toLowerCase();
    }

    public String getNormalizedWord(){
        return word.replaceAll("\"", "").toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultEntry that = (ResultEntry) o;
        return Objects.equals(word, that.word) && Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, line);
    }

    private final String word;
    private final String line;

    public ResultEntry(String word, String line) {
        this.word = word;
        this.line = line;
    }

    public static ResultEntry from(String line, int columnIndex) {
        return new ResultEntry(Parser.getCell(columnIndex, line, false), line);
    }


    @Override
    public String toString() {
        return word + "[" + line + "]";
    }

    public String getLine() {
        return line;
    }

}
