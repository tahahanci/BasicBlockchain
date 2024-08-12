package blockchain;

import utility.BlockchainUtility;

import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public String blockData;
    public long timestamp;
    private int nonce;

    public Block(String blockData, String previousHash) {
        this.blockData = blockData;
        this.previousHash = previousHash;
        timestamp = new Date().getTime();
        hash = calculateHash();
    }

    public String calculateHash() {
        return BlockchainUtility.applySha256(previousHash + timestamp + nonce + blockData);
    }

    public void mine(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block was mined. Hash value: " + hash);
    }
}
