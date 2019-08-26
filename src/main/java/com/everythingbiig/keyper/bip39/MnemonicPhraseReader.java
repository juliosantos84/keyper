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
            System.out.println("Enter " + phraseSize + " words:\n");
            for(int i = 0; i < phrase.length; i++) {
                System.out.print("word #" + i + ":");
                phrase[i] = getWord(reader);
                System.out.println();
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
            System.out.println("The word you entered is not valid, try again.");
            word = br.readLine();
        }
        return word;
    }

    // public static void main(String[] args) {
    //     MnemonicPhraseReader mpr = new MnemonicPhraseReader(3);
    //     MnemonicPhrase mp = mpr.readFromStandardIn();
    //     System.out.println(mp.toText());
    // }
}