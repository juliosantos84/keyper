package com.everythingbiig.keyper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;

import software.amazon.awssdk.core.SdkBytes;

public class KmsSecretManager {
    
    private static final AWSKMS kmsClient = AWSKMSClientBuilder.defaultClient();

    private String kmsKey = null;

    public KmsSecretManager(String kmsKey) {
        this.kmsKey = kmsKey;
    }

    public String getKey() {
        return this.kmsKey;
    }

    public SdkBytes encryptBytes(byte[] plaintext) {
        EncryptRequest req = new EncryptRequest();
        req.setKeyId(getKey());
        req.setPlaintext(ByteBuffer.wrap(plaintext));
        EncryptResult res = kmsClient.encrypt(req);
        return SdkBytes.fromByteBuffer(res.getCiphertextBlob());
    }

    public SdkBytes decryptBytes(SdkBytes cipherblob) {
        DecryptRequest req = new DecryptRequest();
        req.setCiphertextBlob(cipherblob.asByteBuffer());
        DecryptResult res = kmsClient.decrypt(req);
        System.out.println("res.plaintext: " + res.getPlaintext());        
        return SdkBytes.fromByteBuffer(res.getPlaintext());
    }

    public SdkBytes encryptString(String plaintext) {
        return encryptBytes(plaintext.getBytes());
    }

    public String decryptString(SdkBytes cipherblob) {
        return new String(decryptBytes(cipherblob).asByteArray());
    }

    public void writeToFile(String filePath, SdkBytes bytes) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            throw new Exception("The file " + file.getAbsolutePath() + 
                " already exists, please rename/delete manually before proceeding.");
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

    public SdkBytes readFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("The file " + file.getAbsolutePath() + 
                " doesn't exist");
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