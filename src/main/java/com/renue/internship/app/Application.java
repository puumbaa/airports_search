package com.renue.internship.app;

import com.renue.internship.app.version_2.AutocompleteBinarySearchImpl;
import com.renue.internship.parsers.ColumnEntryParser;

import static com.renue.internship.utils.ApplicationUtils.getColumnIndex;

public class Application {
    public static void main(String[] args) {
        ColumnEntryParser parser = new ColumnEntryParser("src\\main\\resources\\airports.csv");
        AutoComplete solver = new AutocompleteBinarySearchImpl(parser, getColumnIndex(args));
        solver.run();
    }
}
