package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bankchallenge.bankchallenge.application.services.BankAccountService;
import com.bankchallenge.bankchallenge.domain.models.Transaction;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;
import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;

@SpringBootTest
public class SendDepositServiceTest {
    @Autowired
    public BankAccountService accService;

    public List<BankAccount> setUpAcc = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        BankAccount acc = accService.createAccount(new BankAccount());
        setUpAcc.add(acc);
    }

    @Test
    public void sendDeposit() {
        BankAccount testacc = setUpAcc.get(0);

        depositService.send(new Transaction(testacc.getId(),100, TRANSACTION_TYPE.DEPOSIT));

        BankAccount accNew = accService.getAccount(testacc.getId());

        assertEquals(100, accNew.getBalance());
        assertEquals(testacc.getId(), accNew.getId());
        assertTrue(testacc.getBalance()+100 == accNew.getBalance());
    }
}
