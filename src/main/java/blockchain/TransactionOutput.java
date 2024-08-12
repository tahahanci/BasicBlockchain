package blockchain;

import utility.BlockchainUtility;

import java.security.PublicKey;

public class TransactionOutput {

    public String ID;
    public PublicKey recipient;
    public float value;
    public String parentTransactionID;

    public TransactionOutput(PublicKey recipient, float value, String parentTransactionID) {
        this.recipient = recipient;
        this.value = value;
        this.parentTransactionID = parentTransactionID;
        ID = BlockchainUtility.applySha256(BlockchainUtility.getStringFromKey(recipient) + value
                + parentTransactionID);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey == recipient;
    }
}
