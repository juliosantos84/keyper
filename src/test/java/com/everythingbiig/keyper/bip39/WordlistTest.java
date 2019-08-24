package com.everythingbiig.keyper.bip39;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.google.gson.Gson;

public class WordlistTest {

    @Test
    public void testGetWordlist() {
        int expectedWordCount = 2048;
        int actualWordCount = Wordlist.getWordlist().length;
        Assertions.assertEquals(expectedWordCount, actualWordCount);
    }
}