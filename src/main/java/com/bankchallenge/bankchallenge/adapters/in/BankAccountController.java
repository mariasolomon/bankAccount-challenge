package com.bankchallenge.bankchallenge.adapters.in;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankchallenge.bankchallenge.application.inputPorts.BankAccountInterface;
import com.bankchallenge.bankchallenge.domain.models.BankAccount;

@RestController
@RequestMapping(value = "/accounts")
public class BankAccountController {
    
    @Autowired
    private BankAccountInterface accInterf;

    @GetMapping(produces = { "application/hal+json" }) 
	public ResponseEntity<CollectionModel<BankAccount>> getAccounts() {
        final Iterable<BankAccount> listAccounts = accInterf.getAccounts();
        
        for (final BankAccount account : listAccounts) {
            Link selfLink = linkTo(BankAccountController.class).slash(account.getId()).withSelfRel();
            account.add(selfLink);
        }
    
        Link link = linkTo(BankAccountController.class).withSelfRel();

        return ResponseEntity.ok(CollectionModel.of(listAccounts, link));
	}
    
    @GetMapping(value="/{id}", produces = { "application/hal+json" })
	public ResponseEntity<EntityModel<BankAccount>> getAccount(@PathVariable("id") Integer id) {
        BankAccount account = accInterf.getAccount(id);

        Link selfLink = linkTo(BankAccountController.class).slash(account.getId()).withSelfRel();
        account.add(selfLink);

        Link link = linkTo(BankAccountController.class).withRel("accounts");
        return ResponseEntity.ok(EntityModel.of(account, link));
	}

    @PostMapping(produces = { "application/hal+json" })
	public ResponseEntity<EntityModel<BankAccount>> createAccount(@RequestBody BankAccount newAcc) {
        BankAccount account = accInterf.createAccount(newAcc);

        Link selfLink = linkTo(BankAccountController.class).slash(account.getId()).withSelfRel();
        account.add(selfLink);

        Link link = linkTo(BankAccountController.class).withRel("accounts");
        return ResponseEntity.ok(EntityModel.of(account, link));
	}

    @PutMapping(value="/{id}", produces = { "application/hal+json" })
	public ResponseEntity<EntityModel<BankAccount>> replaceAccount(@RequestBody BankAccount newAcc, @PathVariable Integer id) {
        BankAccount accountUpdated = accInterf.replaceAccount(newAcc, id);

        Link selfLink = linkTo(BankAccountController.class).slash(accountUpdated.getId()).withSelfRel();
        accountUpdated.add(selfLink);

        Link link = linkTo(BankAccountController.class).withRel("accounts");
        return ResponseEntity.ok(EntityModel.of(accountUpdated, link));
	}
 
    @DeleteMapping(value="/{id}", produces = { "application/hal+json" })
    public void deleteAccount(@PathVariable Integer id) {
        accInterf.deleteAccount(id);
    }

    @DeleteMapping(produces = { "application/hal+json" })
    public void deleteAccounts() {
        accInterf.deleteAccounts();
    }
}
