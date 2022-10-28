package com.epara.exchange.provider;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ExchangeProvider {

    @Value("${exchangeProvider.apikey}")
    String APIKEY;
    @Value("${exchangeProvider.host}")
    String HOST;

    public ExchangeProvider() {
    }

    public String getExchangeRatesByBaseAndTargetCurrencies(String baseCurrency, List<String> targetCurrencies) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        var generatedSymbols = generateSymbols(targetCurrencies);

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(HOST)
                .addPathSegment("fixer")
                .addPathSegment("latest")
                .addQueryParameter("symbols", generatedSymbols)
                .addQueryParameter("base", baseCurrency)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("apikey", APIKEY)
                .method("GET", null)
            .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    private String generateSymbols( List<String> targetCurrencies){
        StringBuilder symbolsBuilder = new StringBuilder();

        for(int i=0; i<targetCurrencies.size();i++){
            if(i==targetCurrencies.size()-1){
                symbolsBuilder.append(targetCurrencies.get(i));
                break;
            }
            symbolsBuilder.append(targetCurrencies.get(i)).append(",");
        }
        return symbolsBuilder.toString();
    }
}
