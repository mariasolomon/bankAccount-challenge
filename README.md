# Bank Account challenge
## Description
The goal of this project is to create a REST Spring Boot application with a hexagonal architecture for a banking use case.
The TDD red/green cycle is adopted and can be viewed in the commits. The commits are divided in 3 main parts : 
1. Business models
2. API adapter
3. Persistence adapter

3 user stories are implemented:
* make a deposit
* make a withdrawal
* check the transactions
## Requirements
For building and running the application you need:

* Java version 17.0.5
* Apache Maven 3.8.6
* Junit 5.8.2

## REST API
Method | Path           | Description                    |
-------|----------------|--------------------------------|
GET    | /accounts      | retrieve all accounts   |
DELETE | /accounts  | remove all accounts   |
POST   | /accounts       | store a new account            |
GET    | /accounts/{id} | retrieve one account by its ID |
PUT    | /accounts/{id}  | update an existing account  by its ID|
DELETE | /accounts/{id}  | remove an account by its ID   |
GET    | /transactions      | retrieve all transactions     |
DELETE | /transactions | remove all transactions      |
POST   | /transactions | store a new transaction |
GET    | /transactions/{id} | retrieve one transaction by its ID |
DELETE | /transactions/{id}  | remove a transaction by its ID   |
GET   | /transactions/check/{id} | check the transactions of the account ID  |
POST   | /transactions/send | send a deposit command  |
POST   | /transactions/withdraw | send a withdrawal command |
