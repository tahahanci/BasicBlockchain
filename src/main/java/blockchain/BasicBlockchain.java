package blockchain;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class BasicBlockchain {

    public static ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) {
        Block genesisBlock = new Block("This is first block", "0");
        Block secondBlock = new Block("This is second block", genesisBlock.hash);
        Block thirdBlock = new Block("This is third block", secondBlock.hash);

        blockchain.add(genesisBlock);
        blockchain.add(secondBlock);
        blockchain.add(thirdBlock);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }
}
