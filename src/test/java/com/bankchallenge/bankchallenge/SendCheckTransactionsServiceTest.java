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
import com.bankchallenge.bankchallenge.application.services.SendCheckTransactionsService;
import com.bankchallenge.bankchallenge.domain.models.Transaction;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;
import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;

@SpringBootTest
public class SendCheckTransactionsServiceTest {
    @Autowired
    public BankAccountService accService;

    public List<BankAccount> setUpAcc = new ArrayList<>();
    public List<Transaction> setUpTr = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        BankAccount acc = accService.createAccount(new BankAccount());
        setUpAcc.add(acc);

        BankAccount acc2 = accService.createAccount(new BankAccount());
        setUpAcc.add(acc2);

        Transaction tr1 = depositService.send(new Transaction(acc.getId(), 700,TRANSACTION_TYPE.DEPOSIT));
        Transaction tr2 = depositService.send(new Transaction(acc.getId(), 500,TRANSACTION_TYPE.DEPOSIT));
        Transaction tr3 = withdrawalService.send(new Transaction(acc.getId(), 100,TRANSACTION_TYPE.WITHDRAWAL));
        Transaction tr4 = withdrawalService.send(new Transaction(acc2.getId(), 100,TRANSACTION_TYPE.WITHDRAWAL));

        setUpTr.add(tr1);
        setUpTr.add(tr2);
        setUpTr.add(tr3);
        setUpTr.add(tr4);
    }

    @Test
    public void sendCheck_account1() {
        BankAccount testacc = setUpAcc.get(0);
        List<Transaction> testListTr = new ArrayList<>();

        for (Transaction tr : setUpTr){
            if (tr.getSrcAccountId() == testacc.getId())
                testListTr.add(tr);
        }


        List<Transaction> listTransactions = checkService.send(testacc.getId());

        assertEquals(testListTr.size(), listTransactions.size());

        for (Transaction tr : listTransactions){
            assertTrue(testListTr.contains(tr));
        }

        BankAccount accNew = accService.getAccount(testacc.getId());

        assertEquals(testacc.getId(), accNew.getId());
        assertEquals(testacc.getBalance() + 1100, accNew.getBalance());
    }

    @Test
    public void sendCheck_account2() {
        BankAccount testacc = setUpAcc.get(1);
        List<Transaction> testListTr = new ArrayList<>();

        for (Transaction tr : setUpTr){
            if (tr.getSrcAccountId() == testacc.getId())
                testListTr.add(tr);
        }


        List<Transaction> listTransactions = checkService.send(testacc.getId());

        assertEquals(testListTr.size(), listTransactions.size());

        for (Transaction tr : listTransactions){
            assertTrue(testListTr.contains(tr));
        }

        BankAccount accNew = accService.getAccount(testacc.getId());

        assertEquals(testacc.getId(), accNew.getId());
        assertEquals(testacc.getBalance() - 100, accNew.getBalance());
    }
}
