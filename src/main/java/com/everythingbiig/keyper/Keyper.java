package com.everythingbiig.keyper;

import java.io.File;

import com.everythingbiig.keyper.bip39.MnemonicPhrase;
import com.everythingbiig.keyper.bip39.MnemonicPhraseReader;

import software.amazon.awssdk.core.SdkBytes;

public class Keyper {

    private static final String KMS_TEST_KEY = "arn:aws:kms:us-east-1:297473205123:key/c539da97-bcd9-448c-9def-1b3cf3ddbded";

    public static void main(String[] args) throws Exception {
        // MnemonicPhraseReader phraseReader = 
        //     new MnemonicPhraseReader(MnemonicPhraseReader.PHRASE_SIZE_DEFAULT, null)=/

        MnemonicPhraseReader phraseReader = 
            new MnemonicPhraseReader(3, null);
        KmsSecretManager secretManager = new KmsSecretManager(KMS_TEST_KEY);

        MnemonicPhrase phrase = phraseReader.readFromStandardIn();

        System.out.println("You entered: \n" + phrase.toText());

        String json = phrase.toJson();

        SdkBytes encryptedBytes = secretManager.encryptString(json);

        File secretFile = new File("./secrets/dev-secret");
        
        secretManager.writeToFile(secretFile.getAbsolutePath(), encryptedBytes);

        SdkBytes encryptedBytesFromFile = secretManager.readFromFile(secretFile.getAbsolutePath());

        String decryptedJson = secretManager.decryptString(encryptedBytesFromFile);

        MnemonicPhrase fromJson = new MnemonicPhrase();
        fromJson.fromJson(decryptedJson);

        System.out.println("Decrypted (from file): \n" + fromJson.toText());

    }
}