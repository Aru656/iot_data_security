import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptoService {

    private static final String KEY_DIR = "keys";
    private static final String PUBLIC_KEY_FILE = "keys/public.key";
    private static final String PRIVATE_KEY_FILE = "keys/private.key";

    private static final int GCM_IV_LENGTH = 12;     // 96 bits 
    private static final int GCM_TAG_LENGTH = 128;   // 128-bit auth tag

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public CryptoService() throws Exception {

        
        Security.addProvider(new BouncyCastleProvider());

        File keyDir = new File(KEY_DIR);
        if (!keyDir.exists()) {
            keyDir.mkdir();
        }

        if (Files.exists(Paths.get(PUBLIC_KEY_FILE)) &&
            Files.exists(Paths.get(PRIVATE_KEY_FILE))) {

            loadKeys();   
        } else {
            generateAndSaveKeys();  
        }
    }

    

    private void generateAndSaveKeys() throws Exception {

        KeyPairGenerator keyGen =
                KeyPairGenerator.getInstance("EC", "BC");
        keyGen.initialize(256);

        KeyPair keyPair = keyGen.generateKeyPair();

        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();

        saveKey(PUBLIC_KEY_FILE, publicKey.getEncoded());
        saveKey(PRIVATE_KEY_FILE, privateKey.getEncoded());
    }

    private void saveKey(String path, byte[] keyBytes) throws Exception {
        Files.write(
                Paths.get(path),
                Base64.getEncoder().encode(keyBytes)
        );
    }

    private void loadKeys() throws Exception {

        byte[] publicKeyBytes =
                Base64.getDecoder().decode(
                        Files.readAllBytes(Paths.get(PUBLIC_KEY_FILE))
                );

        byte[] privateKeyBytes =
                Base64.getDecoder().decode(
                        Files.readAllBytes(Paths.get(PRIVATE_KEY_FILE))
                );

        KeyFactory keyFactory =
                KeyFactory.getInstance("EC", "BC");

        this.publicKey =
                keyFactory.generatePublic(
                        new X509EncodedKeySpec(publicKeyBytes)
                );

        this.privateKey =
                keyFactory.generatePrivate(
                        new PKCS8EncodedKeySpec(privateKeyBytes)
                );
    }

    

    public HybridEncryptedData encrypt(String plainText)
            throws Exception {

        
        KeyGenerator aesGen = KeyGenerator.getInstance("AES");
        aesGen.init(128);
        SecretKey aesKey = aesGen.generateKey();

        
        Cipher aesCipher =
                Cipher.getInstance("AES/GCM/NoPadding");

        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        GCMParameterSpec gcmSpec =
                new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        aesCipher.init(
                Cipher.ENCRYPT_MODE,
                aesKey,
                gcmSpec
        );

        byte[] cipherText =
                aesCipher.doFinal(plainText.getBytes());

        
        byte[] encryptedDataBytes =
                new byte[iv.length + cipherText.length];

        System.arraycopy(iv, 0,
                encryptedDataBytes, 0, iv.length);

        System.arraycopy(cipherText, 0,
                encryptedDataBytes, iv.length, cipherText.length);

        
        Cipher ecCipher =
                Cipher.getInstance("ECIES", "BC");

        ecCipher.init(
                Cipher.ENCRYPT_MODE,
                publicKey
        );

        byte[] encryptedAesKeyBytes =
                ecCipher.doFinal(aesKey.getEncoded());

        
        return new HybridEncryptedData(
                Base64.getEncoder()
                        .encodeToString(encryptedDataBytes),
                Base64.getEncoder()
                        .encodeToString(encryptedAesKeyBytes)
        );
    }
}
