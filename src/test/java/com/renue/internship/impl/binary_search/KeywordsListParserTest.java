package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;
import com.renue.internship.common.Type;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("ColumnEntryListParser is working")
public class KeywordsListParserTest {
    private final Parser<KeywordsList> parser = new KeywordsListParser(FILENAME);
    private static final String FILENAME = "airports-test.csv";

    private static final int LINE_SEPARATOR_LENTGH = System.lineSeparator().getBytes().length;

    @Test
    public void parse_column() {
        KeywordsList expected = new KeywordsList(Arrays.asList(
                new KeywordsList.KeywordEntry("goroka airport", 0),
                new KeywordsList.KeywordEntry("madang airport", 151 + LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("mount hagen kagamuga airport", 296 + 2L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("nadzab airport", 471 + 3L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("port moresby jacksons international airport", 609 + 4L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("wewak international airport", 799 + 5L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("narsarsuaq airport", 956 + 6L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("godthaab / nuuk airport", 1100 + 7L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("kangerlussuaq airport", 1243 + 8L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("thule air base", 1389 + 9L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("egilsstaðir airport", 1521 + 10L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("aguenar – hadj bey akhamok airport", 1674 + 11L * LINE_SEPARATOR_LENTGH),
                new KeywordsList.KeywordEntry("hornafjörður airport", 1833 + 12L * LINE_SEPARATOR_LENTGH)
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
                parser.parseLine(1833 + 12L * LINE_SEPARATOR_LENTGH));
    }

}