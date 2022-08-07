package com.renue.internship.parsers;

import java.util.List;

public interface Parser<T> {
    String parseLine(long charsBefore);
    List<T> parseColumn(int columnIndex);

}
