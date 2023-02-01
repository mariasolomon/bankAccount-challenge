package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
	void createEmptyAccount() {
		BankAccount acc = new BankAccount();

        assertEquals(acc.getBalance(), 0);
	}

    @Test
	void createAccount() {
		BankAccount acc = new BankAccount(50);

        assertEquals(acc.getBalance(), 50);
	}
}
