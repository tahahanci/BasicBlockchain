package blockchain;

import com.google.gson.GsonBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import utility.BlockchainUtility;

import java.security.Security;
import java.util.ArrayList;

public class BasicBlockchain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;

    public static boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes are equal!");
                return false;
            }

            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Hashes are equal!");
                return false;
            }

            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block has not been mined.");
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();

        System.out.println("Private and public keys: ");
        System.out.println(BlockchainUtility.getStringFromKey(walletA.privateKey));
        System.out.println(BlockchainUtility.getStringFromKey(walletA.publicKey));

        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 10, null);
        transaction.generateSignature(walletA.privateKey);

        System.out.println("Is signature verified: " + transaction.verifySignature());
    }
}
