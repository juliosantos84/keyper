package com.everythingbiig.keyper.bip39;

import java.io.File;

import com.everythingbiig.keyper.KmsSecretManager;
import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MnemonicPhraseTest {

    // private static final String kmsKeyDev = "arn:aws:kms:us-east-1:297473205123:key/c539da97-bcd9-448c-9def-1b3cf3ddbded";

    private static final KmsSecretManager secretManager = new KmsSecretManager();

    @Test
    public void testToJson() {
        String[] phrase = getPhrase();
        String expected = new Gson().toJson(phrase);
        String actual = new MnemonicPhrase(phrase).toJson();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFromJson() {
        String[] expectedPhraseArray = { "zoo", "apple", "lend" };
        String jsonPhrase = "[\"zoo\",\"apple\",\"lend\"]";
        MnemonicPhrase expected = new MnemonicPhrase(expectedPhraseArray);
        
        MnemonicPhrase actual = new MnemonicPhrase();
        actual.fromJson(jsonPhrase);
        Assertions.assertEquals(expected, actual);
    }

    public static String[] getPhrase() {
        String[] phrase = new String[24];
        for (int i = 0; i < phrase.length; i++) {
            phrase[i] = "blah" + i;
        }
        return phrase;
    }

    @Test
    public void testWriteReadFromFile() throws Exception {
        File secretFile = new File("./tmp/secret-file");
        secretFile.deleteOnExit();

        String[] phraseArray = {"apple","lend","zone"};

        // Encrypt it
        MnemonicPhrase expectedPhrase = new MnemonicPhrase(phraseArray);
        expectedPhrase.setSecretManager(secretManager);
        expectedPhrase.encryptToFile(secretFile);

        // Decrypt it
        MnemonicPhrase actualPhrase = new MnemonicPhrase();
        actualPhrase.setSecretManager(secretManager);
        actualPhrase.decryptFromFile(secretFile);
        
        Assertions.assertEquals(expectedPhrase, actualPhrase);
    }
}