package com.everythingbiig.keyper.bip39;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MnemonicPhraseReader {

    public final static int PHRASE_SIZE_DEFAULT = 24;

    private int phraseSize;

    private BufferedReader reader = null;

    public MnemonicPhraseReader() {
        this.phraseSize = PHRASE_SIZE_DEFAULT;
    }

    public MnemonicPhraseReader(int phraseSize, BufferedReader reader) {
        this.phraseSize = phraseSize;
        this.reader = reader;
    }

    protected void setBufferedReader(BufferedReader reader) {
        this.reader = reader;
    }

    public MnemonicPhrase readFromStandardIn() {
        if ( this.reader == null ) {
            this.setBufferedReader(new BufferedReader(new InputStreamReader(System.in)));
        }

        String[] phrase = new String[phraseSize];
        try {
            System.out.println(String.format("Enter %02d words:\n", phraseSize));
            for(int i = 0; i < phrase.length; i++) {
                System.out.print(String.format("word %02d: ", (i + 1)));
                phrase[i] = getWord(reader);
                System.out.println("\n");
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ioex) {
                reader = null;
            }
        }
        return new MnemonicPhrase(phrase);
    }

    private String getWord(BufferedReader br) throws IOException {
        String word = br.readLine();
        while(!Wordlist.isValidWord(word)) {
            System.out.print(String.format("'%s' is invalid, try again: ", word));
            word = br.readLine();
            System.out.println("\n");
        }
        return word;
    }
}