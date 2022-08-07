package com.renue.internship.app.version_2;

public class ResultSet {
    public StringBuilder getResult() {
        return result;
    }

    public int getQuantity() {
        return quantity;
    }

    private final StringBuilder result;
    private int quantity;

    public ResultSet() {
        result = new StringBuilder();
        quantity = 0;
    }

    public static ResultSet empty() {
        return new ResultSet();
    }

    public void add(ColumnEntry entry){
        result.append(entry.getCell())
                .append("[")
                .append(entry.getRow())
                .append("]")
                .append("\n");
        quantity++;
    }

}
