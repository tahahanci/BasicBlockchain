package blockchain;

import utility.BlockchainUtility;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public long timestamp;
    private int nonce;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        timestamp = new Date().getTime();
        hash = calculateHash();
    }

    public String calculateHash() {
        return BlockchainUtility.applySha256(previousHash + timestamp + nonce + merkleRoot);
    }

    public void mine(int difficulty) {
        merkleRoot = BlockchainUtility.getMerkleRoot(transactions);
        String target = BlockchainUtility.getDifficultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block was mined. Hash value: " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null)
            return false;

        if (previousHash != "0") {
            if (!transaction.processTransaction()) {
                System.out.println("Transaction failed to process.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction successfully added to block.");
        return true;
    }
}
