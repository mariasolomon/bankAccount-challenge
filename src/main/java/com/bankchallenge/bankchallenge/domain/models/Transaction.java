package com.bankchallenge.bankchallenge.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class Transaction extends RepresentationModel<Transaction>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer transactionId;
    private Integer srcAccountId;
    private Integer amount;
    private TRANSACTION_TYPE type;

    public Transaction(Integer srcAccountId, Integer amount, TRANSACTION_TYPE type) {
        this.srcAccountId = srcAccountId;
        this.amount = amount;
        this.type = type;
    }

    public Transaction() {
        amount = 0;
    }

    public TRANSACTION_TYPE getType() {
        return type;
    }

    public void setType(TRANSACTION_TYPE type) {
        this.type = type;
    }

    public Integer getId() {
        return transactionId;
    }

    public Integer getSrcAccountId() {
        return srcAccountId;
    }

    public void setSrcAccountId(Integer accountId) {
        this.srcAccountId = accountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }    
    
    public String toString() {
        return "{transactionId=" + transactionId + ", srcAccountId=" + srcAccountId
        + ", amount=" + amount + "}";
    }

    public String toJson() {
        return "{ \"transactionId\" :" + transactionId + ", \"srcAccountId\" :"+ srcAccountId 
        + ", \"amount\" :"+ amount + ", \"type\" :\""+ type + "\"}";
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
}
