package com.everythingbiig.keyper.bip39;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MnemonicPhraseReader {

    public MnemonicPhrase readFromStandardIn(int count) {

        String[] phrase = new String[count];
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter " + count + " words:\n");
            for(int i = 0; i < phrase.length; i++) {
                System.out.print("word #" + i + ":");
                String word = br.readLine();
                while(!Wordlist.isValidWord(word)) {
                    System.out.println("> The word you entered is not valid, try again.");
                    word = br.readLine();
                }
                phrase[i] = word;
                System.out.println();
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ioex) {
                br = null;
            }
        }
        return new MnemonicPhrase(phrase);
    }

    public static void main(String[] args) {
        MnemonicPhraseReader mpr = new MnemonicPhraseReader();
        MnemonicPhrase mp = mpr.readFromStandardIn(3);
        System.out.println("Menemonic Phrase: \n" + mp.toText());
    }
}