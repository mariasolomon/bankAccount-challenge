package com.bankchallenge.bankchallenge.application.inputPorts;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

public interface SendDepositUseCase {
    Transaction send(Transaction operation);
}

