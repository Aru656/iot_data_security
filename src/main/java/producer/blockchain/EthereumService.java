package producer.blockchain;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class EthereumService {

    private Web3j web3j;
    private Credentials credentials;

    
    private static final String RPC_URL =
            "https://sepolia.infura.io/v3/35d85636c6144d66b0e01546ebe04217";

    //  Replace with your MetaMask PRIVATE KEY 
    private static final String PRIVATE_KEY =
            "3173f0ddf2ec56e1398362fbef01b358fea57ff0258b076383695542f8a073ec";

    public EthereumService() {

        web3j = Web3j.build(new HttpService(RPC_URL));
        credentials = Credentials.create(PRIVATE_KEY);

        System.out.println("✅ Connected to Ethereum Sepolia");
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}