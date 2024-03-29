package com.everythingbiig.keyper;

import java.nio.ByteBuffer;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;

import software.amazon.awssdk.core.SdkBytes;

public class KmsSecretManager {
    
    private static final String AWS_KMS_KEY_ARN = System.getenv("AWS_KMS_KEY_ARN");

    private AWSKMS kmsClient = null;

    private String kmsKey = null;

    public KmsSecretManager() {
        this.kmsKey = AWS_KMS_KEY_ARN;
        this.kmsClient = AWSKMSClientBuilder.defaultClient();
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
        return SdkBytes.fromByteBuffer(res.getPlaintext());
    }

    public SdkBytes encryptString(String plaintext) {
        return encryptBytes(plaintext.getBytes());
    }

    public String decryptString(SdkBytes cipherblob) {
        return new String(decryptBytes(cipherblob).asByteArray());
    }
}