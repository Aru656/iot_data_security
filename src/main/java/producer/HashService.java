
import java.security.MessageDigest;
import java.util.Base64;

public class HashService {

    public static String sha256(String input) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());

        
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
