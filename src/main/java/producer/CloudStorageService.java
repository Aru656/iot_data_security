import java.io.File;
import java.io.FileWriter;
public class CloudStorageService {

    private static final String ENCRYPTED_DIR = "cloud/encrypted/";
    private static final String METADATA_DIR = "cloud/metadata/";

    public CloudStorageService() {
        new File(ENCRYPTED_DIR).mkdirs();
        new File(METADATA_DIR).mkdirs();
    }

    
    public void storeEncryptedData(String fileName, String encryptedData) throws Exception {

        FileWriter writer = new FileWriter(ENCRYPTED_DIR + fileName);
        writer.write(encryptedData);
        writer.close();
    }

    
    public void storeMetadata(String fileName, String metadataJson) throws Exception {

        FileWriter writer = new FileWriter(METADATA_DIR + fileName);
        writer.write(metadataJson);
        writer.close();
    }
}
