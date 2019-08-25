package com.everythingbiig.keyper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.core.SdkBytes;

public class KmsSecretManagerTest {

    private static final String kmsKeyDev = "arn:aws:kms:us-east-1:297473205123:key/c539da97-bcd9-448c-9def-1b3cf3ddbded";

    private KmsSecretManager mgr = new KmsSecretManager(kmsKeyDev);

    @Test
    public void testEncrypt() {
        String plaintext = "secret";
        SdkBytes cipherblob = mgr.encryptBytes(plaintext.getBytes());
        Assertions.assertNotNull(cipherblob);
    }

    @Test
    public void testEncryptDecrypt() {
        String plaintext = "secret";
        SdkBytes cipherblob = mgr.encryptBytes(plaintext.getBytes());
        SdkBytes decryptedBytes = mgr.decryptBytes(cipherblob);
        String decryptedString = new String(decryptedBytes.asByteArray());
        Assertions.assertEquals(plaintext, decryptedString);
    }

    @Test
    public void testEncryptString() {
        String plaintext = "secret";
        SdkBytes cipherblob = mgr.encryptString(plaintext);
        Assertions.assertNotNull(cipherblob);
    }

    @Test
    public void testEncryptDecryptString() {
        String plaintext = "secret";
        SdkBytes cipherblob = mgr.encryptString(plaintext);
        String decryptedString = mgr.decryptString(cipherblob);
        Assertions.assertEquals(plaintext, decryptedString);
    }
}