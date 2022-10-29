package com.epara.epara.provider;

import java.io.IOException;
import java.util.List;

public interface IExchangeProvider {
    String getExchangeRatesByBaseAndTargetCurrencies(String baseCurrency, List<String> targetCurrencies) throws IOException;

}
