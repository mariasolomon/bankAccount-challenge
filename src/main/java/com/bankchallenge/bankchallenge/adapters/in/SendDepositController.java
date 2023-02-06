package com.bankchallenge.bankchallenge.adapters.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.bankchallenge.bankchallenge.application.inputPorts.SendDepositUseCase;
import com.bankchallenge.bankchallenge.domain.models.Transaction;

@RestController
@RequestMapping(path = "/transactions/send")
public class SendDepositController {

    @Autowired
    private SendDepositUseCase sendPort;

    @PostMapping(produces = { "application/hal+json" })
    public ResponseEntity<EntityModel<Transaction>> send(@RequestBody Transaction operation) {    
        Transaction tr = sendPort.send(operation);
        Link link = linkTo(SendDepositController.class).withRel("sendDeposit");

        return ResponseEntity.ok(EntityModel.of(tr, link));
    }
}