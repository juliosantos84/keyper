package com.everythingbiig.keyper.bip39;

import java.io.File;
import java.util.Arrays;

import com.everythingbiig.keyper.KmsSecretManager;
import com.google.gson.Gson;

import software.amazon.awssdk.core.SdkBytes;

public class MnemonicPhrase {

    private static final String AWS_KMS_KEY_ARN = System.getenv("AWS_KMS_KEY_ARN");

    public static final int PHRASE_SIZE_DEFAULT = 24;

    private String[] phrase = null;

    private KmsSecretManager secretManager = new KmsSecretManager(AWS_KMS_KEY_ARN);

    public MnemonicPhrase() {
        this.phrase = new String[PHRASE_SIZE_DEFAULT];
    }

    public MnemonicPhrase(String[] phrase) {
        this.phrase = phrase;
    }

    public void setPhrase(String[] phrase) {
        this.phrase = phrase;
    }

    public String[] getPhrase() {
        return phrase;
    }

    public String toText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.phrase.length; i++) {
            sb.append(String.format("%02d: %s", (i+1), phrase[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toJson() {
        return new Gson().toJson(this.phrase);
    }

    public void fromJson(String jsonPhrase) {
        this.phrase = new Gson().fromJson(jsonPhrase, new String[0].getClass());
    }

    @Override
    public boolean equals(Object mnemonicPhrase) {
        if ( !(mnemonicPhrase instanceof MnemonicPhrase)) {
            return false;
        }
        return Arrays.equals(this.phrase, ((MnemonicPhrase)mnemonicPhrase).getPhrase());
    }

    public void decryptFromFile(File file) throws Exception {
        SdkBytes encryptedBytesFromFile = secretManager.readFromFile(file.getAbsolutePath());
        String decryptedJson = secretManager.decryptString(encryptedBytesFromFile);
        MnemonicPhrase fromJson = new MnemonicPhrase();
        fromJson.fromJson(decryptedJson);
        this.phrase = fromJson.phrase;
    }

    public void encryptToFile(File file) throws Exception {
        SdkBytes encryptedBytes = secretManager.encryptString(this.toJson());
        secretManager.writeToFile(file.getAbsolutePath(), encryptedBytes);
    }
}