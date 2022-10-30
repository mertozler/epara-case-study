package com.epara.epara.provider;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.epara.epara.utils.ExchangeUtils.*;


@Component
public class FixerExchangeProvider implements IExchangeProvider {

    @Value("${fixerExchangeProvider.apikey}")
    private String APIKEY;


    public FixerExchangeProvider() {
    }

    @Override
    public String getExchangeRatesByBaseAndTargetCurrencies(String baseCurrency, List<String> targetCurrencies) throws IOException {
        validateCurrencyCode(baseCurrency);

        String generatedSymbols = generateSymbols(targetCurrencies);

        Request generatedRequest = generateRequestForFixerAPI(baseCurrency, generatedSymbols, APIKEY);

        Response response = sendRequest(generatedRequest);

        return response.body().string();
    }

     private Response sendRequest(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Response response = client.newCall(request).execute();
        return response;
    }



}
