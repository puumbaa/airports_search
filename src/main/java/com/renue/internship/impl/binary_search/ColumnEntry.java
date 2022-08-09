package com.renue.internship.impl.binary_search;

import java.util.Objects;

public class ColumnEntry {
    private final long bytesBeforeRow;
    private final String cell;

    public ColumnEntry(String cell, long bytesBeforeRow) {
        this.cell = cell;
        this.bytesBeforeRow = bytesBeforeRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnEntry that = (ColumnEntry) o;
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
