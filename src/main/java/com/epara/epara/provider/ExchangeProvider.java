package com.epara.epara.provider;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.epara.epara.utils.ExchangeUtils.validateCurrencyCode;

@Component
public class ExchangeProvider implements IExchangeProvider {

    @Value("${exchangeProvider.apikey}")
    private String APIKEY;


    public ExchangeProvider() {
    }

    @Override
    public String getExchangeRatesByBaseAndTargetCurrencies(String baseCurrency, List<String> targetCurrencies) throws IOException {
        validateCurrencyCode(baseCurrency);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        var generatedSymbols = generateSymbols(targetCurrencies);

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.apilayer.com")
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

    public String generateSymbols(List<String> targetCurrencies) {
        StringBuilder symbolsBuilder = new StringBuilder();

        for(int i=0; i<targetCurrencies.size();i++){
            validateCurrencyCode(targetCurrencies.get(i));
            if(i==targetCurrencies.size()-1){
                symbolsBuilder.append(targetCurrencies.get(i));
                break;
            }
            symbolsBuilder.append(targetCurrencies.get(i)).append(",");
        }
        return symbolsBuilder.toString();
    }


}
