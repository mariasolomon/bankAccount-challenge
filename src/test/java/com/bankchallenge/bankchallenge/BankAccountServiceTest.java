package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.bankchallenge.bankchallenge.application.services.AccountNotFoundException;
import com.bankchallenge.bankchallenge.application.services.BankAccountService;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest
@ContextConfiguration(classes=BankchallengeApplication.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class BankAccountServiceTest {

    @Autowired
    public BankAccountService service;

    public List<BankAccount> setUpAcc = new ArrayList<>();

    @BeforeAll
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
        
        assertThrows(AccountNotFoundException.class,() -> service.getAccount(testAcc.getId()));
    }

    @AfterAll
    public void deleteAccounts() {

        service.deleteAccounts();
        assertThrows(AccountNotFoundException.class,() -> service.getAccounts());
    
    }
}
