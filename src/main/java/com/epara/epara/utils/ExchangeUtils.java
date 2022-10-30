package com.epara.epara.utils;

import com.epara.epara.exception.CurrencyCodeIsNotValidException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ExchangeUtils {
    private static final Pattern CODE = Pattern.compile("[A-Z][A-Z][A-Z]");
    public static void validateCurrencyCode(final String currencyCode) {
        Objects.requireNonNull(currencyCode, "Currency code must not be null");
        if (CODE.matcher(currencyCode).matches() == false) {
            throw new CurrencyCodeIsNotValidException("Currency string code, must be ASCII upper-case letters");
        }
    }

    public static Request generateRequestForFixerAPI(String baseCurrency, String generatedSymbols, String APIKEY){
        HttpUrl generatedUrl = generateUrlForFixerAPI(baseCurrency, generatedSymbols);
        Request request = new Request.Builder()
                .url(generatedUrl)
                .addHeader("apikey", APIKEY)
                .method("GET", null)
                .build();
        return request;
    }

    public static HttpUrl generateUrlForFixerAPI(String baseCurrency, String generatedSymbols){
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.apilayer.com")
                .addPathSegment("fixer")
                .addPathSegment("latest")
                .addQueryParameter("symbols", generatedSymbols)
                .addQueryParameter("base", baseCurrency)
                .build();
        return httpUrl;
    }

    public static String generateSymbols(List<String> targetCurrencies) {
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
