package client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import client.blockchain.BlockchainVerifier;
import client.crypto.HashService;
import client.model.ClientMetadata;
import client.service.ClientDecryptionService;
import common.GoogleDriveService;
import producer.blockchain.IntegrityStorage;

public class ClientMain {

    private static final int RECORDS_TO_DECRYPT = 5;

    public static void main(String[] args) throws Exception {

        GoogleDriveService driveService = new GoogleDriveService();

        String projectFolderId =
                driveService.getOrCreateFolder("Final Year Project");

        String metadataFolderId =
                driveService.getOrCreateSubFolder(
                        projectFolderId, "metadata"
                );

        String encryptedFolderId =
                driveService.getOrCreateSubFolder(
                        projectFolderId, "encrypted"
                );

        
        FileList metadataList =
                driveService.listFilesInFolder(metadataFolderId);

        List<File> metadataFiles = metadataList.getFiles();

        if (metadataFiles == null || metadataFiles.isEmpty()) {
            System.out.println(" No metadata files found");
            return;
        }

        metadataFiles.sort(
                Comparator.comparing(File::getName)
        );

        int limit = Math.min(RECORDS_TO_DECRYPT, metadataFiles.size());

        
        String pemKey =
                Files.lines(Paths.get("keys/private.key"))
                        .filter(line -> !line.startsWith("-----"))
                        .collect(Collectors.joining());

        byte[] keyBytes =
                Base64.getDecoder().decode(pemKey);

        PrivateKey privateKey =
                KeyFactory.getInstance("EC")
                        .generatePrivate(
                                new PKCS8EncodedKeySpec(keyBytes)
                        );

        ObjectMapper mapper = new ObjectMapper();

        
        Web3j web3j = Web3j.build(
                new HttpService("https://sepolia.infura.io/v3/35d85636c6144d66b0e01546ebe04217")
        );

        try {

            Credentials credentials =
                    Credentials.create("3173f0ddf2ec56e1398362fbef01b358fea57ff0258b076383695542f8a073ec");

            String CONTRACT_ADDRESS =
                    "0xf713244Fa1a9A742466781FB70ae6705AB868740";

            IntegrityStorage contract =
                    IntegrityStorage.load(
                            CONTRACT_ADDRESS,
                            web3j,
                            credentials,
                            new DefaultGasProvider()
                    );

            BlockchainVerifier verifier =
                    new BlockchainVerifier(contract);

            
            for (int i = 0; i < limit; i++) {

                File metaFile = metadataFiles.get(i);
                System.out.println("\n Decrypting record "
                        + (i + 1) + " → " + metaFile.getName());

                String timestamp =
                        metaFile.getName()
                                .replace("meta_", "")
                                .replace(".json", "");

                String encryptedFileName =
                        "enc_" + timestamp + ".dat";

                File encryptedFile =
                        driveService
                                .listFilesInFolder(encryptedFolderId)
                                .getFiles()
                                .stream()
                                .filter(f -> encryptedFileName.equals(f.getName()))
                                .findFirst()
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Encrypted file not found: "
                                                        + encryptedFileName
                                        )
                                );

                byte[] metadataBytes =
                        readAllBytes(
                                driveService.downloadFile(metaFile.getId())
                        );

                ClientMetadata metadata =
                        mapper.readValue(
                                metadataBytes,
                                ClientMetadata.class
                        );

                byte[] encryptedAesKey =
                        Base64.getDecoder()
                                .decode(metadata.encrypted_aes_key);

                
                byte[] encryptedDataBase64 =
                        readAllBytes(
                                driveService.downloadFile(
                                        encryptedFile.getId()
                                )
                        );

                
                byte[] encryptedData =
                        Base64.getDecoder().decode(
                                new String(encryptedDataBase64, StandardCharsets.UTF_8)
                        );

                
                String localHash =
                        HashService.sha256(
                                new String(encryptedDataBase64, StandardCharsets.UTF_8)
                        );

                boolean verified =
                        verifier.verifyHash(
                                metadata.device_id,
                                metadata.timestamp,
                                localHash
                        );

                if (!verified) {
                    throw new SecurityException(
                            "Blockchain verification failed! Data may be tampered."
                    );
                }

                
                byte[] plainData =
                        ClientDecryptionService.decrypt(
                                encryptedData,
                                encryptedAesKey,
                                encryptedData.length,
                                privateKey
                        );

                System.out.println(" Decrypted data:");
                System.out.println(
                        new String(plainData, StandardCharsets.UTF_8)
                );
            }

            System.out.println(
                    "\n Successfully decrypted "
                            + limit + " records"
            );

        } finally {

    System.out.println("Shutting down Web3j...");
    web3j.shutdown();

    
    System.exit(0);
}
    }

    private static byte[] readAllBytes(InputStream input)
            throws Exception {

        ByteArrayOutputStream buffer =
                new ByteArrayOutputStream();

        byte[] data = new byte[1024];
        int nRead;

        while ((nRead = input.read(data)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }
}