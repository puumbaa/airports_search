package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Type;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class KeywordsList {

    public KeywordsList(List<KeywordEntry> keywords) {
        this.keywords = keywords;
    }

    private final List<KeywordEntry> keywords;
    private Type type;

    public List<KeywordEntry> getKeywords() {
        return keywords;
    }

    public void add(KeywordEntry keywordEntry) {
        keywords.add(keywordEntry);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static class KeywordEntry {
        private final long bytesBeforeRow;
        private final String cell;

        public KeywordEntry(String cell, long bytesBeforeRow) {
            this.cell = cell;
            this.bytesBeforeRow = bytesBeforeRow;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeywordEntry that = (KeywordEntry) o;
            return bytesBeforeRow == that.bytesBeforeRow && Objects.equals(cell, that.cell);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bytesBeforeRow, cell);
        }

        @Override
        public String toString() {
            return "ColumnEntry{" +
                    "bytesBeforeRow=" + bytesBeforeRow +
                    ", cell='" + cell + '\'' +
                    '}';
        }

        public String getCell() {
            return cell;
        }

        public long getBytesBeforeRow() {
            return bytesBeforeRow;
        }
    }

    public void sort() {
        if (!keywords.isEmpty()) {
            switch (type) {
                case INT:
                    keywords.sort(Comparator.comparingInt(keywordEntry -> Integer.parseInt(keywordEntry.getCell())));
                case DOUBLE:
                    keywords.sort(Comparator.comparingDouble(keywordEntry -> Integer.parseInt(keywordEntry.getCell())));
                case STRING:
                    keywords.sort(Comparator.comparing(KeywordsList.KeywordEntry::getCell));
            }
        }
    }
}
