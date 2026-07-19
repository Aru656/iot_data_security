package client.crypto;

import java.security.PrivateKey;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ECCKeyDecryptor {

    static {
        
        Security.addProvider(new BouncyCastleProvider());
    }

    
    public static SecretKey decryptAESKey(byte[] encryptedAesKey,
                                          PrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] aesKeyBytes = cipher.doFinal(encryptedAesKey);

        return new SecretKeySpec(aesKeyBytes, "AES");
    }
}
