package com.renue.internship.impl.binary_search;

public class ColumnEntry {
    private final long bytesBeforeRow;
    private final String cell;
    private String row;


    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }



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
