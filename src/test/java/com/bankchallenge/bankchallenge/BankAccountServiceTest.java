package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;

@SpringBootTest
public class BankAccountServiceTest {

    public List<BankAccount> setUpAcc = new ArrayList<>();

    @Test
    public void setUp() throws Exception {
        BankAccount acc1 = service.createAccount(new BankAccount(100));
        BankAccount acc2 = service.createAccount(new BankAccount(200));
        BankAccount acc3 = service.createAccount(new BankAccount(200));

        setUpAcc.add(acc1);
        setUpAcc.add(acc2);
        setUpAcc.add(acc3);
    }

    @Test
    public void createAccount() {
        BankAccount acc = service.createAccount(new BankAccount(700));
        assertEquals(700, acc.getBalance());
    }

    @Test
    public void getAccount() {
        BankAccount testAcc = setUpAcc.get(0);
        BankAccount acc = service.getAccount(testAcc.getId());

        assertEquals(testAcc.getId(), acc.getId());
        assertEquals(testAcc.getBalance(), acc.getBalance());
    }

    @Test
    public void getAccounts() {
        Iterable<BankAccount> listeAccounts = service.getAccounts();
        assertEquals(ArrayList.class, listeAccounts.getClass());
    }

    @Test
    public void replaceAccount(){
        BankAccount testAcc = setUpAcc.get(1);
        BankAccount newAcc = service.replaceAccount(new BankAccount(8000), testAcc.getId());

        assertEquals(testAcc.getId(), newAcc.getId());
        assertFalse(testAcc.getBalance() == newAcc.getBalance());
        assertEquals(8000, newAcc.getBalance());
    }

    @Test
    public void deleteAccount(){
        BankAccount testAcc = setUpAcc.get(2);
        service.deleteAccount(testAcc.getId());
        
        assertThrows(AccountNotFoundException,() -> service.getAccount(testAcc.getId()));
    }

    @Test
    public void deleteAccounts() {

        service.deleteAccounts();
        assertThrows(AccountNotFoundException,() -> service.getAccounts());
    
    }
}
