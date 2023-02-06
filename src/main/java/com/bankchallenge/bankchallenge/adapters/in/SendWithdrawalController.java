package com.bankchallenge.bankchallenge.adapters.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankchallenge.bankchallenge.application.inputPorts.SendWithdrawalUseCase;
import com.bankchallenge.bankchallenge.domain.models.Transaction;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "/transactions/withdraw")
public class SendWithdrawalController {

    @Autowired
    private SendWithdrawalUseCase withdrawPort;

    @PostMapping(produces = { "application/hal+json" })
    public ResponseEntity<EntityModel<Transaction>> send(@RequestBody Transaction operation) {    
        Transaction tr = withdrawPort.send(operation);
        Link link = linkTo(SendDepositController.class).withRel("sendWithdraw");
        
        return ResponseEntity.ok(EntityModel.of(tr, link));
    }
}