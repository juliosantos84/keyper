package com.everythingbiig.keyper;

import java.io.File;

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

    @Test
    public void testWriteReadFromFile() throws Exception {
        String expectedText = "Some Text";
        SdkBytes textBytes = SdkBytes.fromByteArray(expectedText.getBytes());

        File secretFile = new File("./tmp/secret-file");
        secretFile.deleteOnExit();

        mgr.writeToFile(secretFile.getAbsolutePath(), textBytes);

        SdkBytes readBytes = mgr.readFromFile(secretFile.getAbsolutePath());
        String actualText = new String(readBytes.asByteArray());

        Assertions.assertEquals(expectedText, actualText);
    }

    // @AfterAll
    // public void cleanup() {
    //     File secretFile = new File("./tmp/secret-file");
    //     if(secretFile.exists()) {
    //         secretFile.deleteO
    //     }
    // }
}