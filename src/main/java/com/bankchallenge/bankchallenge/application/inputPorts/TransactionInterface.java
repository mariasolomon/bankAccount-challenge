package com.bankchallenge.bankchallenge.application.inputPorts;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

public interface TransactionInterface {
    Iterable<Transaction> getTransactions();
    Transaction getTransaction(Integer id);
    Transaction createTransaction(Transaction newOp);
    void deleteTransaction(Integer id);
}
