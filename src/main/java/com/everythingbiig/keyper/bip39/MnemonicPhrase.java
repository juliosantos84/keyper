package com.everythingbiig.keyper.bip39;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import com.everythingbiig.keyper.KmsSecretManager;
import com.google.gson.Gson;

import software.amazon.awssdk.core.SdkBytes;

public class MnemonicPhrase {

    public static final String KEYPER_PHRASE_SIZE = System.getenv("KEYPER_PHRASE_SIZE");

    private String[] phrase = null;

    private KmsSecretManager secretManager = new KmsSecretManager();

    public MnemonicPhrase() {
        this.phrase = new String[defaultPhraseSize()];
    }

    public MnemonicPhrase(int phraseSize) {
        this.phrase = new String[phraseSize];
    }

    public MnemonicPhrase(String json) {
        fromJson(json);
    }

    public MnemonicPhrase(String[] phrase) {
        this.phrase = phrase;
    }

    public static int defaultPhraseSize() {
        int defaultSize = 24;
        if ( KEYPER_PHRASE_SIZE != null && KEYPER_PHRASE_SIZE.length() > 0 ) {
            defaultSize = Integer.parseInt(KEYPER_PHRASE_SIZE);
        }
        return defaultSize;
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

    protected KmsSecretManager getSecretManager() {
        return this.secretManager;
    }
    
    protected void setSecretManager(KmsSecretManager secretManager) {
        this.secretManager = secretManager;
    }

    public void decryptFromFile(File file) throws Exception {
        System.out.println(String.format("Decrypting from file %s using %s", file, getSecretManager().getKey()));
        SdkBytes encryptedBytesFromFile = readFromFile(file.getAbsolutePath());
        String decryptedJson = secretManager.decryptString(encryptedBytesFromFile);
        fromJson(decryptedJson);
        System.out.println(String.format("Done, decrypted %02d chars", toJson().length()));
    }

    public void encryptToFile(File file) throws Exception {
        System.out.println(String.format("Encrypting to file %s using %s", file, getSecretManager().getKey()));
        SdkBytes encryptedBytes = secretManager.encryptString(this.toJson());
        writeToFile(file.getAbsolutePath(), encryptedBytes);
        System.out.println(String.format("Done, %02d bytes written", file.length()));
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