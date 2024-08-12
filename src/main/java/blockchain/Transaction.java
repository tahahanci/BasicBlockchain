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

    public boolean processTransaction() {
        if (verifySignature() == false) {
            System.out.println("Transaction signature failed to verify.");
            return false;
        }

        for (TransactionInput i : inputs) {
            i.UTXO = BasicBlockchain.UTXOs.get(i.transactionOutputID);
        }

        if (getInputsValue() < BasicBlockchain.minimumTransaction) {
            System.out.println("Transaction inputs are too small: " + getInputsValue());
            return false;
        }

        float remain = getInputsValue() - value;
        transactionID = calculateHash();
        outputs.add(new TransactionOutput(this.recipient, value, transactionID));
        outputs.add(new TransactionOutput(this.sender, remain, transactionID));

        for (TransactionOutput o : outputs) {
            BasicBlockchain.UTXOs.put(o.ID, o);
        }

        for (TransactionInput i : inputs) {
            if (i.UTXO == null)
                continue;
            BasicBlockchain.UTXOs.remove(i.UTXO.ID);
        }

        return true;
    }

    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null)
                continue;
            total += i.UTXO.value;
        }
        return total;
    }

    public float getOutputsValue() {
        float total = 0;
        for (TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }
}
