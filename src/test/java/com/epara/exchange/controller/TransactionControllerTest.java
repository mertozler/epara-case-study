package com.epara.exchange.controller;

import com.epara.exchange.IntegrationTestSupport;
import com.epara.exchange.dto.CreateTransactionRequest;
import com.epara.exchange.dto.TransactionDto;
import com.epara.exchange.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port=0",
        "command.line.runner.enabled=false"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class TransactionControllerTest extends IntegrationTestSupport  {
    @Test
    public void testGetTransactionGetById_whenTransactionIdExists_ShouldReturnTransactionDto() throws Exception {
        //given
        Transaction transaction = transactionRepository.save(generateTransaction());
        transactionService.createTransaction(transaction);
        TransactionDto expected = converter.convert(transaction);

        //when
        //then
        this.mockMvc.perform(get(TRANSACTION_API_ENDPOINT + transaction.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload.base", is(expected.getBase())))
                .andExpect(jsonPath("$.payload.id", is(expected.getId())))
                .andExpect(jsonPath("$.payload.rates", is(expected.getRates())))
                .andReturn();

    }

    @Test
    public void testGetTransactionGetById_whenTransactionIdISNotExists_ShouldReturnError() throws Exception {

        this.mockMvc.perform(get(TRANSACTION_API_ENDPOINT + "notexist"))
                .andExpect(status().isNotFound())
                .andReturn();

    }


}