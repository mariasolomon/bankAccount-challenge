package com.bankchallenge.bankchallenge.application.inputPorts;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

public interface SendWithdrawalUseCase {
    Transaction send(Transaction operation);
}
