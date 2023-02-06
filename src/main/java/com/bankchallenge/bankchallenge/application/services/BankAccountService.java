package com.bankchallenge.bankchallenge.application.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankchallenge.bankchallenge.application.inputPorts.BankAccountInterface;
import com.bankchallenge.bankchallenge.application.outputPorts.LoadAccountPort;
import com.bankchallenge.bankchallenge.application.outputPorts.UpdateAccountPort;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;

@Component
public class BankAccountService implements BankAccountInterface{
    
    @Autowired
    private LoadAccountPort loadAccountPort;
    @Autowired
    private UpdateAccountPort updateAccountPort;
    
	public Iterable<BankAccount> getAccounts() {
        final Optional<Iterable<BankAccount>> listAccounts = loadAccountPort.loadAccounts();
        
        if (listAccounts.get().spliterator().getExactSizeIfKnown() == 0){
            throw new AccountNotFoundException("No accounts in the DB");  
        }else{
            return listAccounts.get();
        }   
	}

    public BankAccount getAccount(Integer id) {
        final Optional<BankAccount> account = loadAccountPort.loadAccount(id);

        if (!account.isPresent()){
            throw new AccountNotFoundException("The ID of "+id+" was not found");  
        }else{
            return account.get();
        }
	}

	public BankAccount createAccount(BankAccount newAcc) {
        return updateAccountPort.updateAccount(newAcc);
	}

	public BankAccount replaceAccount(BankAccount newAcc, Integer id) {

        final Optional<BankAccount> account = loadAccountPort.loadAccount(id);
        
        if (!account.isPresent()){
            throw new AccountNotFoundException("id: "+ id);  
        }else{
            account.get().setBalance(newAcc.getBalance());
            return updateAccountPort.updateAccount(account.get());
        } 
	}
 
    public void deleteAccount(Integer id) {
        updateAccountPort.deleteAccount(id);
    }

    public void deleteAccounts() {
        updateAccountPort.deleteAccounts();
    }
}