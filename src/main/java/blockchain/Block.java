package blockchain;

import utility.BlockchainUtility;

import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public String blockData;
    public long timestamp;

    public Block(String blockData, String previousHash) {
        this.blockData = blockData;
        this.previousHash = previousHash;
        timestamp = new Date().getTime();
        hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = BlockchainUtility.applySha256(previousHash + timestamp
                + blockData);
        return calculatedHash;
    }
}
