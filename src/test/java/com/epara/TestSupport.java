package com.epara;

import com.epara.dto.CreateTransactionRequest;
import com.epara.model.Transaction;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSupport {

    public static final String EXCHANGE_API_ENDPOINT = "/v1/api/exchanges/";
    public static final String TRANSACTION_API_ENDPOINT = "/v1/api/transactions/";

    public Instant getCurrentInstant() {
        String instantExpected = "2021-06-15T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), Clock.systemDefaultZone().getZone());

        return Instant.now(clock);
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.ofInstant(getCurrentInstant(), Clock.systemDefaultZone().getZone());
    }

    public Transaction generateTransaction() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("TRY", 18.0);
        return new Transaction("test", LocalDate.now(),true, "base", rates);
    }


    public CreateTransactionRequest generateCreateTransactionRequest(String baseCurrency, List<String> targetCurrencies) {
        return new CreateTransactionRequest(baseCurrency, targetCurrencies);
    }

}
