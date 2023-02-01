package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TransactionTest {
    
    @Test
	void createEmptyTransaction() {
        Transaction tr = new Transaction();
       
        assertEquals(0, tr.getAmount());
        assertEquals(null, tr.getId());
	}

    @Test
	void createTransaction() {
        Transaction tr = new Transaction(1,500, "DEPOSIT");
       
        assertEquals(1,tr.getSrcAccountId());
        assertEquals(500, tr.getAmount());
        assertEquals("DEPOSIT", tr.getType());
        assertEquals(null, tr.getId());
	}
}
