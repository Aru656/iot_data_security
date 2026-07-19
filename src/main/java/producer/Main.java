import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.web3j.tx.gas.DefaultGasProvider;

import common.GoogleDriveService;
import producer.blockchain.EthereumService;
import producer.blockchain.IntegrityStorage;

public class Main {

    private static final int LIMIT = 5;

    public static void main(String[] args) throws Exception {

        GoogleDriveService driveService = new GoogleDriveService();
        CryptoService cryptoService = new CryptoService();

        
        EthereumService ethService = new EthereumService();

        IntegrityStorage contract =
                IntegrityStorage.load(
                        "0xf713244Fa1a9A742466781FB70ae6705AB868740",
                        ethService.getWeb3j(),
                        ethService.getCredentials(),
                        new DefaultGasProvider()
                );

        System.out.println(" Connected to Smart Contract");
        

        CsvDataSource csvSource =
                new CsvDataSource("resources/dataset/sensor_data.csv");

        
        String rootFolderId =
                driveService.getOrCreateFolder("Final Year Project");

        String encryptedFolderId =
                driveService.getOrCreateSubFolder(
                        rootFolderId,
                        "encrypted"
                );

        String metadataFolderId =
                driveService.getOrCreateSubFolder(
                        rootFolderId,
                        "metadata"
                );

        List<SensorData> dataList = csvSource.readSensorData();

        int count = 0;

        for (SensorData data : dataList) {

            if (count >= LIMIT) break;

            
            String json = JsonUtil.toJson(data);

            
            HybridEncryptedData encrypted =
                    cryptoService.encrypt(json);

            
            String hash =
                    HashService.sha256(
                            encrypted.getEncryptedData()
                    );

            
            contract.addRecord(
                    data.getDeviceId(),
                    data.getTimestamp(),
                    hash
            ).send();

            System.out.println(" Hash stored on Ethereum");
            
            String rawTime = data.getTimestamp();

            String id = rawTime
                    .replace(" ", "_")
                    .replace(":", "-");

            
            File encFile = new File("enc_" + id + ".dat");
            try (FileWriter fw = new FileWriter(encFile)) {
                fw.write(encrypted.getEncryptedData());
            }

            driveService.uploadFileToFolder(
                    encryptedFolderId,
                    encFile.getName(),
                    "application/octet-stream",
                    encFile
            );
            encFile.delete();

            
            String metadataJson =
                    "{\n" +
                    "  \"device_id\": \"" + data.getDeviceId() + "\",\n" +
                    "  \"timestamp\": \"" + data.getTimestamp() + "\",\n" +
                    "  \"hash\": \"" + hash + "\",\n" +
                    "  \"encrypted_aes_key\": \"" +
                    encrypted.getEncryptedAesKey() + "\"\n" +
                    "}";

            File metaFile = new File("meta_" + id + ".json");
            try (FileWriter fw = new FileWriter(metaFile)) {
                fw.write(metadataJson);
            }

            driveService.uploadFileToFolder(
                    metadataFolderId,
                    metaFile.getName(),
                    "application/json",
                    metaFile
            );
            metaFile.delete();

            System.out.println(
                    "Uploaded record " + (count + 1)
            );
            count++;
        }

        System.out.println(
                "5 rows uploaded successfully inside Final Year Project"
        );
    }
}