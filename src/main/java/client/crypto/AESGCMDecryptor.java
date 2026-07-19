package client.crypto;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AESGCMDecryptor {

    private static final int IV_LENGTH = 12;       // 12 bytes for GCM
    private static final int TAG_LENGTH = 128;     // 128-bit auth tag

    
    public static byte[] decrypt(byte[] encryptedData,
                                 SecretKey aesKey) throws Exception {

        // Extract IV
        byte[] iv = Arrays.copyOfRange(encryptedData, 0, IV_LENGTH);

        // Extract actual encrypted payload
        byte[] cipherText =
                Arrays.copyOfRange(encryptedData, IV_LENGTH, encryptedData.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);

        return cipher.doFinal(cipherText);
    }
}
