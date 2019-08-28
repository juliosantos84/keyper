package com.everythingbiig.keyper;

import java.io.File;

import com.everythingbiig.keyper.bip39.MnemonicPhrase;
import com.everythingbiig.keyper.bip39.MnemonicPhraseReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import software.amazon.awssdk.core.SdkBytes;

public class Keyper {

    private static CommandLineParser parser = new DefaultParser();
    private static final Options options = new Options();
    
    static {
        options.addOption("d", "decrypt (on-screen only) the given file");
    }
    
    private static final String AWS_KMS_KEY_ARN = System.getenv("AWS_KMS_KEY_ARN");

    private static final int PHRASE_SIZE = Integer.parseInt(System.getenv("KEYPER_PHRASE_SIZE"));

    private static final File SECRET_FILE = new File(System.getenv("KEYPER_SECRET_FILE"));

    public static void main(String[] args) throws Exception {
        CommandLine cmd = parser.parse( options, args);

        MnemonicPhraseReader phraseReader = 
            new MnemonicPhraseReader(PHRASE_SIZE, null);
        KmsSecretManager secretManager = new KmsSecretManager(AWS_KMS_KEY_ARN);

        if (cmd.hasOption("d")) {
            MnemonicPhrase fromJson = readEncryptedFile(secretManager);
            print(fromJson);
            System.exit(0);
        }

        MnemonicPhrase phrase = phraseReader.readFromStandardIn();

        System.out.println("You entered: \n" + phrase.toText());

        String json = phrase.toJson();

        writeEncryptedFile(secretManager, json);

        MnemonicPhrase fromJson = readEncryptedFile(secretManager);

        print(fromJson);

    }

    private static void print(MnemonicPhrase fromJson) {
        System.out.println("Decrypted (from file): \n" + fromJson.toText());
    }

    private static MnemonicPhrase readEncryptedFile(KmsSecretManager secretManager) throws Exception {
        SdkBytes encryptedBytesFromFile = secretManager.readFromFile(SECRET_FILE.getAbsolutePath());
        String decryptedJson = secretManager.decryptString(encryptedBytesFromFile);
        MnemonicPhrase fromJson = new MnemonicPhrase();
        fromJson.fromJson(decryptedJson);
        return fromJson;
    }

    private static void writeEncryptedFile(KmsSecretManager mgr, String json) throws Exception {
        SdkBytes encryptedBytes = mgr.encryptString(json);
        
        mgr.writeToFile(SECRET_FILE.getAbsolutePath(), encryptedBytes);
    }
}