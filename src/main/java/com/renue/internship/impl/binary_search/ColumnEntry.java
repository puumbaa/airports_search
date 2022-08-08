package com.renue.internship.impl.binary_search;

public class ColumnEntry {
    private final long bytesBeforeRow;
    private final String cell;

    public ColumnEntry(String cell, long bytesBeforeRow) {
        this.cell = cell;
        this.bytesBeforeRow = bytesBeforeRow;
    }


    public String getCell() {
        return cell;
    }

    public long getBytesBeforeRow() {
        return bytesBeforeRow;
    }
}
