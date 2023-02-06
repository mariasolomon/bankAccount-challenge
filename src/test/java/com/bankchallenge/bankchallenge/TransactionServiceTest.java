package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bankchallenge.bankchallenge.domain.models.Transaction;
import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;

@SpringBootTest
public class TransactionServiceTest {

    public List<Transaction> setUpTr = new ArrayList<>();

    @Test
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
        
        assertThrows(TransactionNotFoundException,() -> service.getTransaction(testTr.getId()));
    }

    @Test
    public void deleteAccounts() {
        service.deleteTransactions();
        assertThrows(TransactionNotFoundException,() -> service.getTransactions());
    }
}
