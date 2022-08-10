package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;
import com.renue.internship.common.Type;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("ColumnEntryListParser is working")
public class KeywordsListParserTest {
    private final Parser<KeywordsList> parser = new KeywordsListParser(FILENAME);
    private static final String FILENAME = "airports-test.csv";


    @Test
    public void parse_column() {
        KeywordsList expected = new KeywordsList(Arrays.asList(
                new KeywordsList.KeywordEntry("goroka airport", 0),
                new KeywordsList.KeywordEntry("madang airport", 153),
                new KeywordsList.KeywordEntry("mount hagen kagamuga airport", 300),
                new KeywordsList.KeywordEntry("nadzab airport", 477),
                new KeywordsList.KeywordEntry("port moresby jacksons international airport", 617),
                new KeywordsList.KeywordEntry("wewak international airport", 809),
                new KeywordsList.KeywordEntry("narsarsuaq airport", 968),
                new KeywordsList.KeywordEntry("godthaab / nuuk airport", 1114),
                new KeywordsList.KeywordEntry("kangerlussuaq airport", 1259),
                new KeywordsList.KeywordEntry("thule air base", 1407),
                new KeywordsList.KeywordEntry("egilsstaðir airport", 1541),
                new KeywordsList.KeywordEntry("aguenar – hadj bey akhamok airport", 1696),
                new KeywordsList.KeywordEntry("hornafjörður airport", 1857)
        ));
        expected.setType(Type.STRING);
        KeywordsList actual = new KeywordsList(new ArrayList<>());
        parser.parseColumn(1, actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void parse_line() {
        Assertions.assertEquals(
                "13,\"Hornafjörður Airport\",\"Hofn\",\"Iceland\",\"HFN\",\"BIHN\",64.295601,-15.2272,24,0," +
                        "\"N\",\"Atlantic/Reykjavik\",\"airport\",\"OurAirports\"",
                parser.parseLine(1857));
    }

}