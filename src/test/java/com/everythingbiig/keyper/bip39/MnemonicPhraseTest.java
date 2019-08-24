package com.everythingbiig.keyper.bip39;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.google.gson.Gson;

public class MnemonicPhraseTest {

    @Test
    public void testToText() {
        String[] phrase = getPhrase();
        String expected = new Gson().toJson(phrase);
        String actual = new MnemonicPhrase(phrase).toText();
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