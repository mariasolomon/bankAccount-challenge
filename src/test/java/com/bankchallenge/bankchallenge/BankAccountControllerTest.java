package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
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
import org.springframework.test.web.servlet.ResultActions;

import com.bankchallenge.bankchallenge.domain.models.BankAccount;

@SpringBootTest
@ContextConfiguration(classes=BankchallengeApplication.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class BankAccountControllerTest {

    @Autowired
	public MockMvc mockMvc;

    public List<BankAccount> setUpAcc = new ArrayList<>();

    private MediaType contentType = new MediaType("application", "hal+json");

    @BeforeAll
    public void setUp() throws Exception {
        BankAccount account = new BankAccount(85620);

        String jsonResponse = this.mockMvc.perform(post("/accounts")
            .contentType(contentType)
            .content(account.toJson()))
            .andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(jsonResponse);
        account.setAccountId((Integer) obj.get("id"));
        setUpAcc.add(account);

        BankAccount account2 = new BankAccount(80);
        String jsonResponse2 = this.mockMvc.perform(post("/accounts")
            .contentType(contentType)
            .content(account2.toJson()))
            .andReturn().getResponse().getContentAsString();

        JSONObject obj2 = new JSONObject(jsonResponse2);
        account2.setAccountId((Integer) obj2.get("id"));
        setUpAcc.add(account2);

        BankAccount account3 = new BankAccount(2065);
        String jsonResponse3 = this.mockMvc.perform(post("/accounts")
            .contentType(contentType)
            .content(account3.toJson()))
            .andReturn().getResponse().getContentAsString();

        JSONObject obj3 = new JSONObject(jsonResponse3);
        account3.setAccountId((Integer) obj3.get("id"));
        setUpAcc.add(account3);
    }

    @Test 
	public void createAccount() throws Exception {
        BankAccount account = new BankAccount(8800);

		ResultActions response = this.mockMvc.perform(post("/accounts")
            .contentType(contentType)
            .content(account.toJson()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType));


        String jsonResponse = response.andReturn().getResponse().getContentAsString();
        JSONObject obj = new JSONObject(jsonResponse);

        assertEquals(account.getBalance(), obj.get("balance"));
	}

    @Test
	public void getAccount() throws Exception  {
        BankAccount acc = setUpAcc.get(0);

		this.mockMvc.perform( 
            get("/accounts/"+acc.getId().toString())
                .accept(contentType))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.id").value(acc.getId()))
            .andExpect(jsonPath("$.balance").value(acc.getBalance()));
	}

    @Test
	public void updateAccount() throws Exception  {
        BankAccount acc = setUpAcc.get(1);
        BankAccount accPut = new BankAccount(89852);
        
		this.mockMvc.perform( 
            put("/accounts/"+acc.getId().toString())
                .accept(contentType)
                .contentType(contentType)
                .content( accPut.toJson()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.id").value(acc.getId()))
            .andExpect(jsonPath("$.balance").value(accPut.getBalance()));
    }

    @Test
	public void deleteAccount() throws Exception  {
        BankAccount acc = setUpAcc.get(2);

        this.mockMvc.perform( 
            delete("/accounts/"+acc.getId().toString())
                .accept(contentType))
            .andExpect(status().isOk());
    }

    @Test
    public void should_Return404_When_AccountNotFound() throws Exception {
        this.mockMvc.perform(get("/accounts/565"))
                .andExpect(status().isNotFound());
    }

    @AfterAll
    public void should_Return404_When_AccountListNotFound() throws Exception {
        this.mockMvc.perform( 
            delete("/accounts")
                .accept(contentType))
            .andExpect(status().isOk());
        
        this.mockMvc.perform(get("/accounts"))
            .andExpect(status().isNotFound());
    }
}
