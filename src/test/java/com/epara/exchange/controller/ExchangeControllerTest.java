package com.epara.exchange.controller;

import com.epara.exchange.IntegrationTestSupport;
import com.epara.exchange.dto.CreateTransactionRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
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
class ExchangeControllerTest  extends IntegrationTestSupport {
    @Test
    public void testCreateTransaction_WhenBaseAndTargetCurrenciesAreNotNull_shouldCreateTransactionsAndReturnTransactionDto() throws Exception {
        //given
        List<String> targetCurrencies = new ArrayList<>();
        targetCurrencies.add("TRY");
        targetCurrencies.add("USD");
        targetCurrencies.add("EUR");
        String base = "TRY";
        
        CreateTransactionRequest request = generateCreateTransactionRequest(base, targetCurrencies);

        //when
        //then
        this.mockMvc.perform(post(EXCHANGE_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.payload.id", notNullValue()))
                .andExpect(jsonPath("$.payload.base", is(base)))
                .andExpect(jsonPath("$.payload.rates.size()", is(3)));
    }

    @Test
    public void testCreateTransaction_WhenBaseAndTargetCurrenciesIsNotNull_shouldNotCreateTransactionsAndReturnAnValidatonError() throws Exception {
        //given
        List<String> targetCurrencies = new ArrayList<>();
        targetCurrencies.add("TRY");
        targetCurrencies.add("USD");
        targetCurrencies.add("EUR");

        CreateTransactionRequest request = generateCreateTransactionRequest(null, targetCurrencies);

        //when
        //then
        this.mockMvc.perform(post(EXCHANGE_API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

}