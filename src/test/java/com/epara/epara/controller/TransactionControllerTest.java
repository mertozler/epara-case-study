package com.epara.epara.controller;

import com.epara.epara.IntegrationTestSupport;
import com.epara.epara.dto.TransactionDto;
import com.epara.epara.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void testfindTransactionByDateRange_whenTransactionsAreNotExists_ShouldReturnAnTransactionListIsEmptyException() throws Exception {
        //given
        String startDate = "15/10/2040";
        String endDate = "10/10/2050";
        //when
        //then
        this.mockMvc.perform(get(TRANSACTION_API_ENDPOINT)
                        .queryParam("startDate", startDate)
                        .queryParam("endDate",endDate))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.errors.details", is("TransactionListIsEmptyException")))
                .andReturn();

    }

    @Test
    public void testfindTransactionByDateRange_whenTransactionsAreExists_ShouldReturnTransactionDtoList() throws Exception {
        //given
        String startDate = "15/10/2020";
        String endDate = "10/10/2030";

        Transaction transaction = transactionRepository.save(generateTransaction());
        Transaction transaction2 = transactionRepository.save(generateTransaction());
        Transaction transaction3 = transactionRepository.save(generateTransaction());

        transactionService.createTransaction(transaction);
        transactionService.createTransaction(transaction2);
        transactionService.createTransaction(transaction3);


        //when
        //then
        this.mockMvc.perform(get(TRANSACTION_API_ENDPOINT)
                        .queryParam("startDate", startDate)
                        .queryParam("endDate",endDate))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload", notNullValue()))
                .andReturn();

    }


    @Test
    public void testGetTransactionGetById_whenTransactionIdISNotExists_ShouldReturnError() throws Exception {

        this.mockMvc.perform(get(TRANSACTION_API_ENDPOINT + "notexist"))
                .andExpect(status().isNotFound())
                .andReturn();

    }


}