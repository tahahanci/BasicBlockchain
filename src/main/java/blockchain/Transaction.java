package blockchain;

import utility.BlockchainUtility;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {

    private static int sequence = 0;
    public String transactionID;
    public PublicKey sender;
    public PublicKey recipient;
    public float value;
    public byte[] signature;
    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    public Transaction(PublicKey sender, PublicKey recipient, float value, ArrayList<TransactionInput> inputs) {
        this.sender = sender;
        this.recipient = recipient;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash() {
        sequence++;
        return BlockchainUtility.applySha256(BlockchainUtility.getStringFromKey(sender)
                + BlockchainUtility.getStringFromKey(recipient) + value + sequence);
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = BlockchainUtility.getStringFromKey(sender) + BlockchainUtility.getStringFromKey(recipient)
                + value;
        signature = BlockchainUtility.applyECDSASignature(privateKey, data);
    }

    public boolean verifySignature() {
        String data = BlockchainUtility.getStringFromKey(sender) + BlockchainUtility.getStringFromKey(recipient)
                + value;
        return BlockchainUtility.verifyECDSASignature(sender, data, signature);
    }
}
