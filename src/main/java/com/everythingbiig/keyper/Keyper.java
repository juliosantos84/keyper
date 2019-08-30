package com.everythingbiig.keyper;

import java.io.File;

import com.everythingbiig.keyper.bip39.MnemonicPhrase;
import com.everythingbiig.keyper.bip39.MnemonicPhraseReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class Keyper {

    private static CommandLineParser parser = new DefaultParser();
    private static final Options options = new Options();
    
    static {
        options.addOption("d", "decrypt (on-screen only) the given file");
    }

    private static final File SECRET_FILE = new File(System.getenv("KEYPER_SECRET_FILE"));

    public static void main(String[] args) throws Exception {
        CommandLine cmd = parser.parse( options, args);

        MnemonicPhraseReader phraseReader = 
            new MnemonicPhraseReader();

        if (cmd.hasOption("d")) {
            // Decrypt from the given file
            MnemonicPhrase fromJson = decryptFromFile(SECRET_FILE);
            print(fromJson);
        } else {
            if(SECRET_FILE.exists()) {
                throw new Exception(
                    String.format("The file %s already exists, please rename/delete manually before proceeding.", 
                    SECRET_FILE.getAbsolutePath()
                    )
                );
            }
            // Capture & encrypt to given file
            MnemonicPhrase phrase = phraseReader.readFromStandardIn();

            System.out.println("You entered: \n" + phrase.toText());
    
            encryptToFile(phrase, SECRET_FILE);
    
            System.out.println("\n");
            
            MnemonicPhrase fromJson = decryptFromFile(SECRET_FILE);

            print(fromJson);
        }
    }

    private static void print(MnemonicPhrase fromJson) {
        System.out.println("Decrypted (from file): \n" + fromJson.toText());
    }

    private static MnemonicPhrase decryptFromFile(File file) throws Exception {
        MnemonicPhrase fromFile = new MnemonicPhrase();
        fromFile.decryptFromFile(file);
        return fromFile;
    }

    private static void encryptToFile(MnemonicPhrase phrase, File file) throws Exception {
        phrase.encryptToFile(file);
    }
}