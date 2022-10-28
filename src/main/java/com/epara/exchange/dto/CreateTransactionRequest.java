package com.epara.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;;
import javax.validation.constraints.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    @NotBlank(message = "Base currency not to be null")
    private String baseCurrency;
    @NotEmpty(message = "Target currencies not be null")
    private List<String> targetCurrencies;

}
