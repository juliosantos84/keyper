package com.everythingbiig.keyper.bip39;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    protected void setSecretManager(KmsSecretManager secretManager) {
        this.secretManager = secretManager;
    }
    
    public void decryptFromFile(File file) throws Exception {
        SdkBytes encryptedBytesFromFile = readFromFile(file.getAbsolutePath());
        String decryptedJson = secretManager.decryptString(encryptedBytesFromFile);
        MnemonicPhrase fromJson = new MnemonicPhrase();
        fromJson.fromJson(decryptedJson);
        this.phrase = fromJson.phrase;
    }

    public void encryptToFile(File file) throws Exception {
        SdkBytes encryptedBytes = secretManager.encryptString(this.toJson());
        writeToFile(file.getAbsolutePath(), encryptedBytes);
    }

    protected void writeToFile(String filePath, SdkBytes bytes) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            throw new Exception(
                String.format("The file %s already exists, please rename/delete manually before proceeding.", 
                file.getAbsolutePath())
            );
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes.asByteArray());
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    protected SdkBytes readFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception(String.format("The file %s doesn't exist", file.getAbsolutePath()));
        }
        byte[] fileContents = new byte[ (int) file.length() ];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            
            fis.read(fileContents);

        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return SdkBytes.fromByteArray(fileContents);
    }
}