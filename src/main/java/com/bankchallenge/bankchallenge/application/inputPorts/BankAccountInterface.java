package com.bankchallenge.bankchallenge.application.inputPorts;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;

public interface BankAccountInterface {
    Iterable<BankAccount> getAccounts();
    BankAccount getAccount(Integer id);
    BankAccount createAccount(BankAccount newAcc);
    BankAccount replaceAccount(BankAccount newAcc, Integer id);
    void deleteAccount(Integer id);
    void deleteAccounts();
}
