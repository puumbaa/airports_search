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

    @BeforeAll
    public static void create_test_file() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write(
                    "1,\"Goroka Airport\",\"Goroka\",\"Papua New Guinea\",\"GKA\",\"AYGA\",-6.081689834590001,145.391998291,5282,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"\n" +
                            "2,\"Madang Airport\",\"Madang\",\"Papua New Guinea\",\"MAG\",\"AYMD\",-5.20707988739,145.789001465,20,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"\n" +
                            "3,\"Mount Hagen Kagamuga Airport\",\"Mount Hagen\",\"Papua New Guinea\",\"HGU\",\"AYMH\",-5.826789855957031,144.29600524902344,5388,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"\n" +
                            "4,\"Nadzab Airport\",\"Nadzab\",\"Papua New Guinea\",\"LAE\",\"AYNZ\",-6.569803,146.725977,239,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"\n" +
                            "5,\"Port Moresby Jacksons International Airport\",\"Port Moresby\",\"Papua New Guinea\",\"POM\",\"AYPY\",-9.443380355834961,147.22000122070312,146,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"\n" +
                            "6,\"Wewak International Airport\",\"Wewak\",\"Papua New Guinea\",\"WWK\",\"AYWK\",-3.58383011818,143.669006348,19,10,\"U\",\"Pacific/Port_Moresby\",\"airport\",\"OurAirports\"\n" +
                            "7,\"Narsarsuaq Airport\",\"Narssarssuaq\",\"Greenland\",\"UAK\",\"BGBW\",61.1604995728,-45.4259986877,112,-3,\"E\",\"America/Godthab\",\"airport\",\"OurAirports\"\n" +
                            "8,\"Godthaab / Nuuk Airport\",\"Godthaab\",\"Greenland\",\"GOH\",\"BGGH\",64.19090271,-51.6781005859,283,-3,\"E\",\"America/Godthab\",\"airport\",\"OurAirports\"\n" +
                            "9,\"Kangerlussuaq Airport\",\"Sondrestrom\",\"Greenland\",\"SFJ\",\"BGSF\",67.0122218992,-50.7116031647,165,-3,\"E\",\"America/Godthab\",\"airport\",\"OurAirports\"\n" +
                            "10,\"Thule Air Base\",\"Thule\",\"Greenland\",\"THU\",\"BGTL\",76.5311965942,-68.7032012939,251,-4,\"E\",\"America/Thule\",\"airport\",\"OurAirports\"\n" +
                            "12,\"Egilsstaðir Airport\",\"Egilsstadir\",\"Iceland\",\"EGS\",\"BIEG\",65.2833023071289,-14.401399612426758,76,0,\"N\",\"Atlantic/Reykjavik\",\"airport\",\"OurAirports\"\n" +
                            "216,\"Aguenar – Hadj Bey Akhamok Airport\",\"Tamanrasset\",\"Algeria\",\"TMR\",\"DAAT\",22.8115005493,5.45107984543,4518,1,\"N\",\"Africa/Algiers\",\"airport\",\"OurAirports\"\n" +
                            "13,\"Hornafjörður Airport\",\"Hofn\",\"Iceland\",\"HFN\",\"BIHN\",64.295601,-15.2272,24,0,\"N\",\"Atlantic/Reykjavik\",\"airport\",\"OurAirports\"\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
                parser.parseLine(1845));
    }

}