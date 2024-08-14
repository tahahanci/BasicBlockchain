# 🧱 Basic Blockchain

This is a **basic implementation** of a blockchain written in Java. The project demonstrates the fundamental concepts of blockchain technology, including blocks, hashing, and chaining blocks together in a secure manner.

## ✨ Features

- **Block Creation**: Each block contains an index, a timestamp, a list of transactions, a nonce, and the hash of the previous block.
- **Hash Calculation**: The hash of each block is calculated based on its content and the hash of the previous block, ensuring the integrity of the blockchain.
- **Proof of Work**: A simple proof of work algorithm is implemented to simulate the mining process.
- **Blockchain Integrity Check**: The blockchain verifies that all blocks are linked correctly by comparing the stored hash of the previous block with the calculated hash.