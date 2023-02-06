package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.bankchallenge.bankchallenge.application.services.TransactionNotFoundException;
import com.bankchallenge.bankchallenge.application.services.TransactionService;
import com.bankchallenge.bankchallenge.domain.models.Transaction;
import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@SpringBootTest
@ContextConfiguration(classes=BankchallengeApplication.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionServiceTest {
    @Autowired
    public TransactionService service;

    public List<Transaction> setUpTr = new ArrayList<>();

    @BeforeAll
    public void setUp() throws Exception {
        Transaction tr1 = service.createTransaction(new Transaction(1,100, TRANSACTION_TYPE.DEPOSIT));
        Transaction tr2 = service.createTransaction(new Transaction(1,200, TRANSACTION_TYPE.WITHDRAWAL));

        setUpTr.add(tr1);
        setUpTr.add(tr2);
    }

    @Test
    public void createTransaction() {
        Transaction tr = service.createTransaction(new Transaction(1,300, TRANSACTION_TYPE.DEPOSIT));
        
        assertEquals(300, tr.getAmount());
        assertEquals(1, tr.getSrcAccountId());
        assertEquals(TRANSACTION_TYPE.DEPOSIT, tr.getType());
    }

    @Test
    public void getTransaction() {
        Transaction testTr = setUpTr.get(0);
        Transaction tr = service.getTransaction(testTr.getId());

        assertEquals(testTr.getAmount(), tr.getAmount());
        assertEquals(testTr.getSrcAccountId(), tr.getSrcAccountId());
        assertEquals(testTr.getType(), tr.getType());
    }

    @Test
    public void getTransactions() {
        Iterable<Transaction> listeTransations = service.getTransactions();
        assertEquals(ArrayList.class, listeTransations.getClass());
    }

    @Test
    public void deleteTransaction(){
        Transaction testTr = setUpTr.get(1);
        service.deleteTransaction(testTr.getId());
        
        assertThrows(TransactionNotFoundException.class,() -> service.getTransaction(testTr.getId()));
    }

    @AfterAll
    public void deleteAccounts() {
        service.deleteTransactions();
        assertThrows(TransactionNotFoundException.class,() -> service.getTransactions());
    }
}
