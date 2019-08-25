package com.everythingbiig.keyper.bip39;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Wordlist {

    private static final String wordlistPath = "/wordlist.txt";

    private static String[] wordlist = null;    

    public static String[] getWordlist() {
        if (wordlist != null) {
            System.out.println("Returning wordlist");
            return wordlist;
        }

        wordlist = getWordlistFromFile();

        return wordlist;
    }

    public static Set<String> getWordlistSet() {
        return new HashSet<String>(Arrays.asList(getWordlist()));
    }

    private static String[] getWordlistFromFile() {
        System.out.println("> Lazy loading the wordlist from file.");
        List<String> wordlistTmp = new ArrayList<String>();
         
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(
                new InputStreamReader(
                    Wordlist.class.getResourceAsStream(wordlistPath)));

            String word = null;
            while( (word = reader.readLine()) != null) {
                wordlistTmp.add(word);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
            wordlistTmp.clear(); // Don't return an impartial list
        } finally {
            if ( reader != null ) {
                try {
                    reader.close();
                } catch (IOException ioex) {
                    reader = null;
                }
            }
        }
        System.out.println("> Loaded a wordlist of size: " + wordlistTmp.size());
        return wordlistTmp.toArray(new String[0]);
    }

    public static boolean isValidWord(String word) {
        return getWordlistSet().contains(word);
    }
}