package com.bankchallenge.bankchallenge.application.outputPorts;

import java.util.ArrayList;
import java.util.Optional;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

public interface LoadTransactionPort {
    public Optional<Transaction> loadTransaction(Integer TransactionId);
    public Optional<ArrayList<Transaction>> loadTransactionsAccoundID(Integer accountId);
    public Optional<Iterable<Transaction>> loadTransactions();
}
