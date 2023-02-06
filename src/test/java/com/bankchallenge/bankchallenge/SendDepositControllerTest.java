package com.bankchallenge.bankchallenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;
import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;
import com.bankchallenge.bankchallenge.domain.models.Transaction;

@SpringBootTest
@ContextConfiguration(classes=BankchallengeApplication.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class SendDepositControllerTest {

    @Autowired
	public MockMvc mockMvc;
    
    public List<BankAccount> setUpTr = new ArrayList<>();

    private MediaType contentType = new MediaType("application", "hal+json");

    @BeforeAll 
	public void setUp() throws Exception {
        BankAccount account = new BankAccount(87845);

		String jsonResponse = this.mockMvc.perform(post("/accounts")
            .contentType(contentType)
            .content(account.toJson()))
            .andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(jsonResponse);
        account.setAccountId((Integer) obj.get("id"));
        setUpTr.add(account);
    }

    @Test 
	public void sendDeposit() throws Exception {
        BankAccount acc = setUpTr.get(0);
        Transaction operation = new Transaction(acc.getId(), 500,TRANSACTION_TYPE.DEPOSIT);

		this.mockMvc.perform(post("/transactions/send")
            .contentType(contentType)
            .content(operation.toJson()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType));

        //Get result balance
        this.mockMvc.perform(get("/accounts/"+acc.getId())
            .accept(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.balance").value(acc.getBalance() + 500));
	}

    @Test
    public void should_Return404_When_AccountNotFound() throws Exception {
        Transaction operation = new Transaction(7517, 500,TRANSACTION_TYPE.DEPOSIT);

		this.mockMvc.perform(post("/transactions/send")
            .contentType(contentType)
            .content(operation.toJson()))
            .andExpect(status().isNotFound());
    }
}
