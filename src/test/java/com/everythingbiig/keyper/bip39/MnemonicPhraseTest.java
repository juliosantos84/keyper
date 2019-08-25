package com.everythingbiig.keyper.bip39;

import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MnemonicPhraseTest {

    @Test
    public void testToJson() {
        String[] phrase = getPhrase();
        String expected = new Gson().toJson(phrase);
        String actual = new MnemonicPhrase(phrase).toJson();
        Assertions.assertEquals(expected, actual);
    }

    public static String[] getPhrase() {
        String[] phrase = new String[24];
        for (int i = 0; i < phrase.length; i++) {
            phrase[i] = "blah" + i;
        }
        return phrase;
    }
}