package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("ColumnEntryListParser is working")
public class ColumnEntryListParserTest {
    private final Parser<List<ColumnEntry>> parser = new ColumnEntryListParser(FILENAME);
    private static final String FILENAME = "airports-test.csv";

    @Test
    public void parse_column() {
        List<ColumnEntry> expected = Arrays.asList(
                new ColumnEntry("goroka airport", 0),
                new ColumnEntry("madang airport", 152),
                new ColumnEntry("mount hagen kagamuga airport", 298),
                new ColumnEntry("nadzab airport", 474),
                new ColumnEntry("port moresby jacksons international airport", 613),
                new ColumnEntry("wewak international airport", 804),
                new ColumnEntry("narsarsuaq airport", 962),
                new ColumnEntry("godthaab / nuuk airport", 1107),
                new ColumnEntry("kangerlussuaq airport", 1251),
                new ColumnEntry("thule air base", 1398),
                new ColumnEntry("egilsstaðir airport", 1531),
                new ColumnEntry("aguenar – hadj bey akhamok airport", 1685),
                new ColumnEntry("hornafjörður airport", 1845)
        );
        List<ColumnEntry> actual = new ArrayList<>();
        parser.parseColumn(1, actual);
        System.out.println(actual.equals(expected));
        System.out.println(actual);
        System.out.println(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void parse_line() {
        Assertions.assertEquals(
                "13,\"Hornafjörður Airport\",\"Hofn\",\"Iceland\",\"HFN\",\"BIHN\",64.295601,-15.2272,24,0," +
                        "\"N\",\"Atlantic/Reykjavik\",\"airport\",\"OurAirports\"",
                parser.parseLine(1845));
    }

}
