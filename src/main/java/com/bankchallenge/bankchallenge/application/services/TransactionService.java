package com.bankchallenge.bankchallenge.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.bankchallenge.bankchallenge.application.inputPorts.TransactionInterface;
import com.bankchallenge.bankchallenge.application.outputPorts.LoadTransactionPort;
import com.bankchallenge.bankchallenge.application.outputPorts.UpdateTransactionPort;
import com.bankchallenge.bankchallenge.domain.models.Transaction;


@RestController
@Component
public class TransactionService implements TransactionInterface{
    @Autowired
    private LoadTransactionPort loadTransactionPort;

    @Autowired
    private UpdateTransactionPort updateTransactionPort;
    
	public Iterable<Transaction> getTransactions() {
		final Optional<Iterable<Transaction>> listTransactions = loadTransactionPort.loadTransactions();
        
        if (listTransactions.get().spliterator().getExactSizeIfKnown() == 0){
            throw new TransactionNotFoundException("No Transactions in the DB");  
        }else{
            return listTransactions.get();
        }   
	}
    
	public Transaction getTransaction(Integer id) {
        final Optional<Transaction> transaction = loadTransactionPort.loadTransaction(id);

        if (!transaction.isPresent()){
            throw new TransactionNotFoundException("The ID of "+id+" was not found");  
        }else{
            return transaction.get();
        }
	}

    public List<Transaction> getTransactionAccountId(Integer accountId) {
        final Optional<ArrayList<Transaction>> listTransactions = loadTransactionPort.loadTransactionsAccoundID(accountId);

        if (listTransactions.get().spliterator().getExactSizeIfKnown() == 0){
            throw new TransactionNotFoundException("The accountID of "+accountId+" has no transactions yet");  
        }else{
            return listTransactions.get();
        }
	}

	public Transaction createTransaction(Transaction newOp) {
        return updateTransactionPort.updateTransaction(newOp);
	}
 
    public void deleteTransaction(Integer id) {
        updateTransactionPort.deleteTransaction(id);
    }

    public void deleteTransactions() {
        updateTransactionPort.deleteTransactions();
    }
}
