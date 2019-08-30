package com.everythingbiig.keyper.bip39;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordlistTest {

    @Test
    public void testGetWordlist() {
        int expectedWordCount = 2048;
        int actualWordCount = Wordlist.getWordlist().length;
        Assertions.assertEquals(expectedWordCount, actualWordCount);
    }

    @Test
    public void testGetWordlistValues() {
        String[] expectedValues = new String[2048];
        expectedValues[0] = "abandon"; // First
        expectedValues[1023] = "lend"; // Middle
        expectedValues[2047] = "zoo"; // Last
        String[] actualValues = Wordlist.getWordlist();
        Assertions.assertEquals(expectedValues[0], actualValues[0]);
        Assertions.assertEquals(expectedValues[1023], actualValues[1023]);
        Assertions.assertEquals(expectedValues[2047], actualValues[2047]);
    }
}