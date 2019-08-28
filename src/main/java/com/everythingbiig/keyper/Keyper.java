package com.everythingbiig.keyper;

import java.io.File;

import com.everythingbiig.keyper.bip39.MnemonicPhrase;
import com.everythingbiig.keyper.bip39.MnemonicPhraseReader;

import software.amazon.awssdk.core.SdkBytes;

public class Keyper {

    private static final String KMS_TEST_KEY = System.getenv("AWS_KMS_KEY_ARN");

    private static final int PHRASE_SIZE = Integer.parseInt(System.getenv("KEYPER_PHRASE_SIZE"));

    private static final String SECRET_FILE = System.getenv("KEYPER_SECRET_FILE");

    public static void main(String[] args) throws Exception {
        MnemonicPhraseReader phraseReader = 
            new MnemonicPhraseReader(PHRASE_SIZE, null);
        KmsSecretManager secretManager = new KmsSecretManager(KMS_TEST_KEY);

        MnemonicPhrase phrase = phraseReader.readFromStandardIn();

        System.out.println("You entered: \n" + phrase.toText());

        String json = phrase.toJson();

        SdkBytes encryptedBytes = secretManager.encryptString(json);

        File secretFile = new File(SECRET_FILE);
        
        secretManager.writeToFile(secretFile.getAbsolutePath(), encryptedBytes);

        SdkBytes encryptedBytesFromFile = secretManager.readFromFile(secretFile.getAbsolutePath());

        String decryptedJson = secretManager.decryptString(encryptedBytesFromFile);

        MnemonicPhrase fromJson = new MnemonicPhrase();
        fromJson.fromJson(decryptedJson);

        System.out.println("Decrypted (from file): \n" + fromJson.toText());

    }
}