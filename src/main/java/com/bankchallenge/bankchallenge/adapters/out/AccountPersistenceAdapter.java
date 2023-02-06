package com.bankchallenge.bankchallenge.adapters.out;

import java.util.Optional;
import org.springframework.stereotype.Component;

import com.bankchallenge.bankchallenge.application.outputPorts.LoadAccountPort;
import com.bankchallenge.bankchallenge.application.outputPorts.UpdateAccountPort;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountPort  {

    private final BankAccountRepo accountRepository;

    @Override
    public Optional<BankAccount> loadAccount(Integer accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Optional<Iterable<BankAccount>> loadAccounts() {
        return Optional.ofNullable(accountRepository.findAll());
    }

    @Override
    public BankAccount updateAccount(BankAccount account) {
        return accountRepository.save(account);  
    }

    @Override
    public void deleteAccount(Integer accountID) {
        accountRepository.deleteById(accountID);
    }

    @Override
    public void deleteAccounts() {
        accountRepository.deleteAll();
    }
}
