package client.blockchain;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3jConfig {

    public static Web3j buildWeb3j() {
        return Web3j.build(
            new HttpService("https://sepolia.infura.io/v3/35d85636c6144d66b0e01546ebe04217")
        );
    }
}