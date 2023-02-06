package com.bankchallenge.bankchallenge.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class BankAccount extends RepresentationModel<BankAccount> {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) 
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

    @Override
    public String toString() {
        return "{id:" + accountId + "; balance:" + balance + "}";
    }

    public String toJson() {
        return "{ \"balance\":" + balance + "}";
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
