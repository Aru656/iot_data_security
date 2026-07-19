
public class HybridEncryptedData {

    private String encryptedData;
    private String encryptedAesKey;

    public HybridEncryptedData(String encryptedData, String encryptedAesKey) {
        this.encryptedData = encryptedData;
        this.encryptedAesKey = encryptedAesKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public String getEncryptedAesKey() {
        return encryptedAesKey;
    }
}
