package com.bankchallenge.bankchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
public class SendCheckTransactionsControllerTest {
    @Autowired
	public MockMvc mockMvc;

    public List<Transaction> setUpTr = new ArrayList<>();
    public List<BankAccount> setUpAc = new ArrayList<>();
    private MediaType contentType = new MediaType("application", "hal+json");

    @BeforeAll
	public void setUp() throws Exception {
        BankAccount account = new BankAccount();
        String jsonResponse = this.mockMvc.perform(post("/accounts")
            .contentType(contentType)
            .content(account.toJson()))
            .andReturn().getResponse().getContentAsString();

        JSONObject obj = new JSONObject(jsonResponse);
        account.setAccountId((Integer) obj.get("id"));
        setUpAc.add(account);

		Transaction operation1 = new Transaction(account.getId(), 500,TRANSACTION_TYPE.DEPOSIT);
		String jsonResponse2 = this.mockMvc.perform(post("/transactions/send")
            .contentType(contentType)
            .content(operation1.toJson()))
            .andReturn().getResponse().getContentAsString();
        
        JSONObject obj2 = new JSONObject(jsonResponse2);
        operation1.setTransactionId((Integer) obj2.get("id"));
        setUpTr.add(operation1);

        Transaction operation2 = new Transaction(account.getId(), 10,TRANSACTION_TYPE.WITHDRAWAL);
        String jsonResponse3 = this.mockMvc.perform(post("/transactions/withdraw")
            .contentType(contentType)
            .content(operation2.toJson()))
            .andDo(print())
            .andReturn().getResponse().getContentAsString();
        
        JSONObject obj3 = new JSONObject(jsonResponse3);
        operation2.setTransactionId((Integer) obj3.get("id"));
        setUpTr.add(operation2);
    }
    
    @Test
	public void checkAccountId() throws Exception  {
        BankAccount acc = setUpAc.get(0);
		String jsonResponse = this.mockMvc.perform( 
            get("/transactions/check/"+acc.getId().toString())
                .accept(contentType))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andDo(print())
            .andReturn().getResponse().getContentAsString();
        

        JSONObject obj = new JSONObject(jsonResponse);
        JSONArray respTrans = obj.getJSONObject("_embedded").getJSONArray("transactionList");
        assertEquals(2, respTrans.length());

        Transaction tr;
        JSONObject jsonOp = respTrans.getJSONObject(0);
        if (jsonOp.get("type").toString().equals("DEPOSIT"))
            tr = new Transaction((Integer)jsonOp.get("srcAccountId"), (Integer)jsonOp.get("amount"), TRANSACTION_TYPE.DEPOSIT); 
        else
            tr = new Transaction((Integer)jsonOp.get("srcAccountId"), (Integer)jsonOp.get("amount"),TRANSACTION_TYPE.WITHDRAWAL); 

        tr.setTransactionId((Integer) jsonOp.get("id"));
        assertTrue(setUpTr.contains(tr));

        JSONObject jsonOp1 = respTrans.getJSONObject(1);
        Transaction tr1;
        if (jsonOp1.get("type").toString().equals("DEPOSIT"))
            tr1 = new Transaction((Integer)jsonOp1.get("srcAccountId"), (Integer)jsonOp1.get("amount"), TRANSACTION_TYPE.DEPOSIT); 
        else
            tr1 = new Transaction((Integer)jsonOp1.get("srcAccountId"), (Integer)jsonOp1.get("amount"),TRANSACTION_TYPE.WITHDRAWAL); 

        tr1.setTransactionId((Integer) jsonOp1.get("id"));
        assertTrue(setUpTr.contains(tr1));
    }
}
