package com.renue.internship.impl.trie;

import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Trie is working")
public class TrieTest {
    private final Trie trie = new Trie(4);

    @BeforeEach
    public void fill() {
        trie.insert("12", 0);
        trie.insert("13", 100);
        trie.insert("1", 200);
        trie.insert("25", 300);
        trie.insert("26", 400);
        trie.insert("2", 500);
    }

    @AfterEach
    public void clean() {
        trie.clear();
    }

    @Test
    public void insert() {
        Trie expected = new Trie(
                new Trie.Node('\0', Map.of(
                        '1', new Trie.Node('1', Map.of(
                                '2', new Trie.Node('2', 0),
                                '3', new Trie.Node('3', 100)),
                                Set.of(0, 100, 200)),
                        '2', new Trie.Node('2', Map.of(
                                '5', new Trie.Node('5', 300),
                                '6', new Trie.Node('6', 400)),
                                Set.of(300, 400, 500)))),
                4);

        Assertions.assertEquals(expected, trie);
    }

    @Test
    public void multi_hits() {
        Assertions.assertEquals(trie.hits("1"), Set.of(0, 100, 200));
    }

    @Test
    public void no_hits(){
        Assertions.assertEquals(trie.hits("255"), Collections.emptySet());
    }
}
