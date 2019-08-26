package com.everythingbiig.keyper.bip39;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;

import org.junit.jupiter.api.Test;


public class MnemonicPhraseReaderTest {

    @Test
    public void testGetFromStandardIn() throws Exception {
        BufferedReader reader = mock(BufferedReader.class);
        
        MnemonicPhraseReader phraseReader = new MnemonicPhraseReader(3, reader);

        when(reader.readLine()).thenReturn("apple");
        when(reader.readLine()).thenReturn("lend");
        when(reader.readLine()).thenReturn("zoo");

        phraseReader.readFromStandardIn();

        verify(reader, times(3)).readLine();
    }

    @Test
    public void testGetFromStandardInBadWord() throws Exception {
        BufferedReader reader = mock(BufferedReader.class);
        
        MnemonicPhraseReader phraseReader = new MnemonicPhraseReader(3, reader);

        when(reader.readLine()).thenReturn("julio", "apple", "lend", "zooop", "zoo");

        phraseReader.readFromStandardIn();

        verify(reader, times(5)).readLine();
    }

}