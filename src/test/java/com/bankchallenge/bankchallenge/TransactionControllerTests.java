package com.bankchallenge.bankchallenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.bankchallenge.bankchallenge.domain.models.TRANSACTION_TYPE;
import com.bankchallenge.bankchallenge.domain.models.Transaction;

@SpringBootTest
@ContextConfiguration(classes=BankchallengeApplication.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionControllerTests {
    @Autowired
	public MockMvc mockMvc;

    public List<Transaction> setUpTr = new ArrayList<>();

    private MediaType contentType = new MediaType("application", "hal+json");

    @BeforeAll
	public void setUp() throws Exception {
        Transaction transaction = new Transaction(1,100, TRANSACTION_TYPE.DEPOSIT);

		String jsonResponse = this.mockMvc.perform(post("/transactions")
            .contentType(contentType)
            .content(transaction.toJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(jsonResponse);
        transaction.setTransactionId((Integer) obj.get("id"));
        setUpTr.add(transaction);

        Transaction transaction2 = new Transaction(2,5872, TRANSACTION_TYPE.WITHDRAWAL);

        String jsonResponse2 = this.mockMvc.perform(post("/transactions")
            .contentType(contentType)
            .content(transaction2.toJson()))
            .andReturn().getResponse().getContentAsString();
        
        JSONObject obj2 = new JSONObject(jsonResponse2);
        transaction2.setTransactionId((Integer) obj2.get("id"));
        setUpTr.add(transaction2);
    
    }

    @Test 
	public void createTransaction() throws Exception {
        Transaction transaction = new Transaction(3,89851, TRANSACTION_TYPE.DEPOSIT);

		this.mockMvc.perform(post("/transactions")
            .contentType(contentType)
            .content(transaction.toJson()))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.srcAccountId").value(transaction.getSrcAccountId()))
            .andExpect(jsonPath("$.amount").value(transaction.getAmount()))
            .andExpect(jsonPath("$.type").value(transaction.getType().toString()));
	}

    @Test
	public void getOneTransaction() throws Exception  {
        Transaction transaction = setUpTr.get(0);

		this.mockMvc.perform( 
            get("/transactions/"+transaction.getId().toString())
                .accept(contentType))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.id").value(transaction.getId()))
            .andExpect(jsonPath("$.srcAccountId").value(transaction.getSrcAccountId()))
            .andExpect(jsonPath("$.amount").value(transaction.getAmount()))
            .andExpect(jsonPath("$.type").value(transaction.getType().toString()));
	}

    @Test
	public void deleteTransaction() throws Exception  {
        Transaction transaction = setUpTr.get(1);

        this.mockMvc.perform( 
            delete("/transactions/"+transaction.getId().toString())
                .accept(contentType))
            .andExpect(status().isOk());
    }

    @Test
    public void should_Return404_When_TransactionNotFound() throws Exception {
        this.mockMvc.perform(get("/transactions/185"))
                .andExpect(status().isNotFound());
    }

    @AfterAll
    public void should_Return404_When_TransactionListNotFound() throws Exception {
        this.mockMvc.perform( 
            delete("/transactions")
                .accept(contentType))
            .andExpect(status().isOk());
        
        this.mockMvc.perform(get("/transactions"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}