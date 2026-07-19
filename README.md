


mvn clean compile
mvn exec:java


<mainClass>client.ClientMain</mainClass>
<mainClass>Main</mainClass>

for mining transaction txn use - https://sepolia-faucet.pk910.de/#/
sepolia pow faucet

go to - https://sepolia.etherscan.io
use - 0xf713244Fa1a9A742466781FB70ae6705AB868740


infura https://sepolia.infura.io/v3/35d85636c6144d66b0e01546ebe04217
metamask




pk (Confidential) - cannot share create own metamask account and link the private key 
then change necessary adresses for the project with new ones 


# IoT Data Security using Blockchain

## Overview
A Java-based application that secures IoT sensor data using hybrid encryption (AES + ECC), stores encrypted data in Google Drive, and verifies data integrity using Ethereum Blockchain.

## Features
- Hybrid Encryption (AES-GCM + ECC)
- SHA-256 Hashing
- Google Drive Cloud Storage
- Ethereum Sepolia Blockchain
- Data Integrity Verification
- Java 17 + Maven

## Technologies Used
- Java 17
- Maven
- Web3j
- Bouncy Castle
- Google Drive API
- Ethereum Sepolia

## Project Structure
src/
cloud/
resources/
README.md

## How to Run
mvn clean compile
mvn exec:java