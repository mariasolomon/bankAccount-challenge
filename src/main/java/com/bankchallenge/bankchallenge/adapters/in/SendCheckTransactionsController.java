package com.bankchallenge.bankchallenge.adapters.in;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.bankchallenge.bankchallenge.application.inputPorts.SendCheckTransactionsUseCase;
import com.bankchallenge.bankchallenge.domain.models.Transaction;

@RestController
@RequestMapping(path = "/transactions/check")
public class SendCheckTransactionsController {
    
    @Autowired
    private SendCheckTransactionsUseCase sendCheckOps;

    @GetMapping(value="/{sourceAccountId}", produces = { "application/hal+json" })
    public ResponseEntity<CollectionModel<Transaction>> send(@PathVariable("sourceAccountId") Integer sourceAccountId) {
        List<Transaction> listOperations = sendCheckOps.send(sourceAccountId);

        for (final Transaction transaction : listOperations) {
            Link selfLink = linkTo(TransactionController.class).slash(transaction.getId()).withSelfRel();
            transaction.add(selfLink);
        }

        Link link = linkTo(TransactionController.class).withRel("transactions");
        return ResponseEntity.ok(CollectionModel.of(listOperations, link));
    }
}