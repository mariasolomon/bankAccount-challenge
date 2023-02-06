package com.bankchallenge.bankchallenge.application.outputPorts;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

public interface UpdateTransactionPort {
    public Transaction updateTransaction(Transaction account);
    public void deleteTransaction(Integer TransactionID);
    public void deleteTransactions();
}
