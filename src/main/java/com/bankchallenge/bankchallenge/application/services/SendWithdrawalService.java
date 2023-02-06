package com.bankchallenge.bankchallenge.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankchallenge.bankchallenge.application.inputPorts.SendWithdrawalUseCase;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;
import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;
import com.bankchallenge.bankchallenge.domain.models.Transaction;

@Component
public class SendWithdrawalService  implements SendWithdrawalUseCase{
    @Autowired
    private BankAccountService accService;

    @Autowired
    private TransactionService opService;

    @Override
    public Transaction send(Transaction operation) {
        operation.setType(TRANSACTION_TYPE.WITHDRAWAL);
        opService.createTransaction(operation);

        BankAccount src = accService.getAccount(operation.getSrcAccountId());

        src.setBalance(src.getBalance() - operation.getAmount());
        accService.replaceAccount(src, src.getId());

        return operation;
    }

}
