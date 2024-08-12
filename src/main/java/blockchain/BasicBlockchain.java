package blockchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class BasicBlockchain {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

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
        blockchain.add(new Block("Genesis block is created", "0"));
        System.out.println("Trying to mine genesis block.");
        blockchain.get(0).mine(difficulty);

        blockchain.add(new Block("Second block is created", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to mine second block.");
        blockchain.get(1).mine(difficulty);

        blockchain.add(new Block("Third block is created", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to mine third block.");
        blockchain.get(2).mine(difficulty);

        System.out.println("Is blockchain valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe blockchain: ");
        System.out.println(blockchainJson);
    }
}
