package com.bankchallenge.bankchallenge.adapters.out;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bankchallenge.bankchallenge.application.outputPorts.LoadTransactionPort;
import com.bankchallenge.bankchallenge.application.outputPorts.UpdateTransactionPort;
import com.bankchallenge.bankchallenge.domain.models.Transaction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TransactionPersistenceAdapter implements LoadTransactionPort, UpdateTransactionPort{
    private final TransactionRepo transactionRepository;
    
    @Override
    public Optional<ArrayList<Transaction>> loadTransactionsAccoundID(Integer accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Override
    public Optional<Transaction> loadTransaction(Integer transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public Optional<Iterable<Transaction>> loadTransactions() {
        return Optional.ofNullable(transactionRepository.findAll());
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Integer transactionID) {
        transactionRepository.deleteById(transactionID);
    }

    @Override
    public void deleteTransactions() {
        transactionRepository.deleteAll();   
    }
}
