package com.bankchallenge.bankchallenge.adapters.out;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bankchallenge.bankchallenge.domain.models.Transaction;

@Repository
public interface TransactionRepo extends CrudRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.srcAccountId =?1")
    public Optional<ArrayList<Transaction>> findByAccountId(Integer accountId);

}



