package blockchain;

public class BasicBlockchain {

    public static void main(String[] args) {
        Block genesisBlock = new Block("This is first block", "0");
        System.out.println("Genesis block hash: " + genesisBlock.calculateHash());

        Block secondBlock = new Block("This is second block", genesisBlock.hash);
        System.out.println("Second block hash: " + secondBlock.hash);

        Block thirdBlock = new Block("This is third block", secondBlock.hash);
        System.out.println("Third block hash: " + thirdBlock.hash);
    }
}
