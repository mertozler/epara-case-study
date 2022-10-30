package com.epara.epara.utils;

import com.epara.epara.exception.CurrencyCodeIsNotValidException;
import okhttp3.HttpUrl;
import okhttp3.Request;

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



}
