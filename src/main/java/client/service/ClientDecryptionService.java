package client.service;

import java.security.PrivateKey;

import javax.crypto.SecretKey;

import client.crypto.AESGCMDecryptor;
import client.crypto.ECCKeyDecryptor;

public class ClientDecryptionService {

    // 1 MB memory threshold 
    private static final long MAX_ALLOWED_SIZE = 1024 * 1024;

    
    public static byte[] decrypt(byte[] encryptedData,
                                 byte[] encryptedAesKey,
                                 long encryptedDataSize,
                                 PrivateKey privateKey) throws Exception {

        //  Memory threshold check
        if (encryptedDataSize > MAX_ALLOWED_SIZE) {
            throw new SecurityException(
                    "Encrypted data exceeds allowed memory threshold"
            );
        }

        
        SecretKey aesKey =
                ECCKeyDecryptor.decryptAESKey(encryptedAesKey, privateKey);

        
        return AESGCMDecryptor.decrypt(encryptedData, aesKey);
    }
}
