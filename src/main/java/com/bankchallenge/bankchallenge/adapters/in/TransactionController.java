package com.bankchallenge.bankchallenge.adapters.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bankchallenge.bankchallenge.application.services.TransactionService;
import com.bankchallenge.bankchallenge.domain.models.Transaction;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@Component
@RequestMapping(value = "/transactions")
public class TransactionController {
    @Autowired
    private TransactionService opService;

    @GetMapping(produces = { "application/hal+json" }) 
	public ResponseEntity<CollectionModel<Transaction>> getTransactions() {
        Iterable<Transaction> listTransactions = opService.getTransactions();

        for (final Transaction transaction : listTransactions) {
            Link selfLink = linkTo(TransactionController.class).slash(transaction.getId()).withSelfRel();
            transaction.add(selfLink);
        }
    
        Link link = linkTo(TransactionController.class).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(listTransactions, link));
	}

    @PostMapping(produces = { "application/hal+json" })
	public ResponseEntity<EntityModel<Transaction>> createTransaction(@RequestBody Transaction newOp) {
        Transaction transaction = opService.createTransaction(newOp);

        Link selfLink = linkTo(TransactionController.class).slash(transaction.getId()).withSelfRel();
        transaction.add(selfLink);

        Link link = linkTo(TransactionController.class).withRel("transactions");

        return ResponseEntity.ok(EntityModel.of(transaction, link));
	}
 
    @GetMapping(value="/{id}", produces = { "application/hal+json" })
	public ResponseEntity<EntityModel<Transaction>> getTransaction(@PathVariable("id") Integer id) {
        Transaction transaction = opService.getTransaction(id);

        Link selfLink = linkTo(TransactionController.class).slash(transaction.getId()).withSelfRel();
        transaction.add(selfLink);

        Link link = linkTo(TransactionController.class).withRel("transactions");
        return ResponseEntity.ok(EntityModel.of(transaction, link));
	}

    @DeleteMapping(value="/{id}", produces = { "application/hal+json" })
    public void deleteTransaction(@PathVariable Integer id) {
        opService.deleteTransaction(id);
    }

    @DeleteMapping( produces = { "application/hal+json" })
    public void deleteTransactions() {
        opService.deleteTransactions();
    }
}
