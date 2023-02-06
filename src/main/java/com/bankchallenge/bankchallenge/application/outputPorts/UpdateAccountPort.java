package com.bankchallenge.bankchallenge.application.outputPorts;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;

public interface UpdateAccountPort {
    public BankAccount updateAccount(BankAccount account);
    public void deleteAccount(Integer accountID);
    public void deleteAccounts();
}
