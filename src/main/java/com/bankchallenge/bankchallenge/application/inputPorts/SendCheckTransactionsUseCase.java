package com.bankchallenge.bankchallenge.application.inputPorts;

import java.util.List;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

public interface SendCheckTransactionsUseCase {
    List<Transaction> send(Integer accountID);
}
