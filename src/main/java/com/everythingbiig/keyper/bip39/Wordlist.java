package com.everythingbiig.keyper.bip39;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wordlist {

    private static final String wordlistPath = "wordlist.txt";

    private static String[] wordlist = null;
    private static Set<String> wordlistSet = null;
    

    public static String[] getWordlist() {
        if (wordlist != null) {
            System.out.println("Returning wordlist");
            return wordlist;
        }

        wordlist = getWordlistFromFile();
        
        System.out.println("Returning " + wordlist);
        return wordlist;
    }

    public static Set<String> getWordlistSet() {
        return new HashSet<String>(Arrays.asList(getWordlist()));
    }

    private static String[] getWordlistFromFile() {
        List<String> wordlistTmp = new ArrayList<String>();
         
        BufferedReader reader = null;
        
        try {
            File wordlistFile = new File(
                Wordlist.class.getClassLoader().getResource(wordlistPath).getFile());

            if ( !wordlistFile.exists() ) {
                throw new FileNotFoundException(wordlistPath);
            }

            reader = new BufferedReader(new FileReader(wordlistFile));

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
        return wordlistTmp.toArray(new String[0]);
    }

    public static boolean isValidWord(String word) {
        Set<String> wordlist = getWordlistSet();
        return wordlist.contains(word);
    }
}