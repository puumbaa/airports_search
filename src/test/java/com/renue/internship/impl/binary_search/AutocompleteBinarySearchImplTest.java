package com.renue.internship.impl.binary_search;

import com.renue.internship.common.Parser;
import com.renue.internship.common.ResultEntry;
import com.renue.internship.common.Type;
import com.renue.internship.util.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutocompleteBinarySearchImplTest {
    @Mock
    private Parser<KeywordsList> parser;
    private final KeywordsList keywordsList = new KeywordsList(new ArrayList<>());

    private AutocompleteBinarySearchImpl autocompleteBinarySearch;

    @BeforeEach
    public void setup() {
        doAnswer(invocationOnMock -> {
            KeywordsList keywords = invocationOnMock.getArgument(1);
            keywords.add(new KeywordsList.KeywordEntry("hornafjörður airport", 1845));
            keywords.setType(Type.STRING);
            return null;
        }).when(parser).parseColumn(2, keywordsList);
        when(parser.parseLine(1845)).thenReturn("13,\"Hornafjörður Airport\",\"Hofn\",\"Iceland\",\"HFN\",\"BIHN\",64.295601,-15.2272,24,0,\"N\",\"Atlantic/Reykjavik\",\"airport\",\"OurAirports\"");
        autocompleteBinarySearch = new AutocompleteBinarySearchImpl(parser, keywordsList, false);
    }

    @Test
    public void get_result_set() {
        InputStream stdin = System.in;
        try {
            List<ResultEntry> expected = Collections.singletonList(
                    new ResultEntry(
                            "\"Hornafjörður Airport\"",
                            "13,\"Hornafjörður Airport\",\"Hofn\",\"Iceland\",\"HFN\"," +
                                    "\"BIHN\",64.295601,-15.2272,24,0,\"N\",\"Atlantic/Reykjavik\"," +
                                    "\"airport\",\"OurAirports\""
                    )
            );
            Field columnIndexField = IOUtils.class.getDeclaredField("columnIndex");
            columnIndexField.setAccessible(true);
            columnIndexField.setInt(null,1);

            Field resultSetField = autocompleteBinarySearch.getClass().getDeclaredField("resultSet");
            resultSetField.setAccessible(true);

            System.setIn(new ByteArrayInputStream("Hor\n!quit\n".getBytes(UTF_8)));
            autocompleteBinarySearch.run(2);
            List<ResultEntry> resultSet = (List<ResultEntry>) resultSetField.get(autocompleteBinarySearch);
            Assertions.assertEquals(expected, resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.setIn(stdin);
        }
    }
}
