package blockchain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {

    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecGenParameterSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public float getBalance() {
        float total = 0;

        for (Map.Entry<String, TransactionOutput> item : BasicBlockchain.UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) {
                UTXOs.put(UTXO.ID, UTXO);
                total += UTXO.value;
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey recipient, float value) {
        if (getBalance() < value) {
            System.out.println("Not enough fund for transaction.");
            return null;
        }

        ArrayList<TransactionInput> transactionInputs = new ArrayList<>();
        float total = 0;

        for (Map.Entry<String, TransactionOutput> items : UTXOs.entrySet()) {
            TransactionOutput UTXO = items.getValue();
            total += UTXO.value;
            transactionInputs.add(new TransactionInput(UTXO.ID));

            if (total > value)
                break;
        }

        Transaction transaction = new Transaction(publicKey, recipient, value, transactionInputs);
        transaction.generateSignature(privateKey);

        for (TransactionInput input : transactionInputs) {
            UTXOs.remove(input.transactionOutputID);
        }

        return transaction;
    }
}
