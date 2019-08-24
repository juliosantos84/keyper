package com.everythingbiig.keyper.bip39;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Wordlist {

    private static final String wordlistPath = "wordlist.txt";

    private static String[] wordlist = null;

    public static String[] getWordlist() {
        if (wordlist != null) {
            System.out.println("Returning wordlist");
            return wordlist;
        }

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
                System.out.println("Read word: " + word);
                wordlistTmp.add(word);
                System.out.println("Total words: " + wordlistTmp.size());
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
        wordlist = wordlistTmp.toArray(new String[0]);
        System.out.println("Returning " + wordlist);
        return wordlist;
    }
}