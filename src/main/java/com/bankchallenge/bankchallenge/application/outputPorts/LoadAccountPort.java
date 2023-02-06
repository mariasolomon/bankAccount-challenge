package com.bankchallenge.bankchallenge.application.outputPorts;

import java.util.Optional;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;


public interface LoadAccountPort {
    
    public Optional<BankAccount> loadAccount(Integer accountId);
    public Optional<Iterable<BankAccount>> loadAccounts();
}
