package com.renue.internship.app.version_3;

public class KeywordEntry {
    private final String keyword;
    private final long charsBefore;


    public KeywordEntry(String keyword, long charsBefore) {
        this.keyword = keyword;
        this.charsBefore = charsBefore;
    }


    public String getKeyword() {
        return keyword;
    }

    public long getCharsBefore() {
        return charsBefore;
    }
}
