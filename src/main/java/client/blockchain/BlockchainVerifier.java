package client.blockchain;

import java.math.BigInteger;

import producer.blockchain.IntegrityStorage;

public class BlockchainVerifier {

    private final IntegrityStorage contract;

    
    public BlockchainVerifier(IntegrityStorage contract) {
        this.contract = contract;
    }

    
    public boolean verifyHash(
            String deviceId,
            String timestamp,
            String localHash) throws Exception {

        System.out.println(" Checking blockchain integrity...");

        // Total records stored on-chain
        BigInteger count = contract.getCount().send();

        for (int i = 0; i < count.intValue(); i++) {

            // Solidity returns:
            // (deviceId, timestamp, hashValue)
            var record = contract
                    .getRecord(BigInteger.valueOf(i))
                    .send();

            String chainDeviceId = record.component1();
            String chainTimestamp = record.component2();
            String chainHash = record.component3();

            // Match metadata
            if (chainDeviceId.equals(deviceId)
                    && chainTimestamp.equals(timestamp)) {

                System.out.println("Local Hash  : " + localHash);
                System.out.println("Chain Hash  : " + chainHash);

                boolean match =
                        localHash.equalsIgnoreCase(chainHash);

                if (match) {
                    System.out.println(" Blockchain Integrity Verified");
                } else {
                    System.out.println(" HASH MISMATCH — Possible Tampering!");
                }

                return match;
            }
        }

        System.out.println(" No matching record found on blockchain!");
        return false;
    }
}