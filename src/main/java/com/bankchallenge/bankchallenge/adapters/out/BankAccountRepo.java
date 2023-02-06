package com.bankchallenge.bankchallenge.adapters.out;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;

@Repository
public interface BankAccountRepo extends CrudRepository<BankAccount, Integer>{
    
}
