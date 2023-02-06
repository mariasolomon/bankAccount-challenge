package com.bankchallenge.bankchallenge.domain.models;

public class BankAccount {

    private Integer accountId;
    private Integer balance;
  
    public BankAccount() {
        this.balance = 0;
    }

    public BankAccount(Integer balance) {
        this.balance = balance;
    }

    public Integer getId(){
        return this.accountId;
    }

    public Integer getBalance(){
        return this.balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
