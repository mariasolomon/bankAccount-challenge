package com.bankchallenge.bankchallenge.application.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.bankchallenge.bankchallenge.application.inputPorts.SendCheckTransactionsUseCase;
import com.bankchallenge.bankchallenge.domain.models.Transaction;

@RestController
@Component
public class SendCheckTransactionsService implements SendCheckTransactionsUseCase{
    @Autowired
    private TransactionService opService;

    @Override
    public List<Transaction> send(Integer accountId) {
        return opService.getTransactionAccountId(accountId);   
	}
}
