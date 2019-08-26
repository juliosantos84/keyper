package com.everythingbiig.keyper;

import com.everythingbiig.keyper.bip39.MnemonicPhrase;
import com.everythingbiig.keyper.bip39.MnemonicPhraseReader;

import software.amazon.awssdk.core.SdkBytes;

public class Keyper {

    private static final String KMS_TEST_KEY = "arn:aws:kms:us-east-1:297473205123:key/c539da97-bcd9-448c-9def-1b3cf3ddbded";

    public static void main(String[] args) throws Exception {
        MnemonicPhraseReader phraseReader = 
            new MnemonicPhraseReader(MnemonicPhraseReader.PHRASE_SIZE_DEFAULT, null);
        KmsSecretManager secretManager = new KmsSecretManager(KMS_TEST_KEY);

        MnemonicPhrase phrase = phraseReader.readFromStandardIn();

        System.out.println("You entered: \n" + phrase.toText());

        String json = phrase.toJson();

        SdkBytes encryptedBytes = secretManager.encryptString(json);

        String decryptedJson = secretManager.decryptString(encryptedBytes);

        System.out.println("Decrypted: \n" + decryptedJson);

    }
}