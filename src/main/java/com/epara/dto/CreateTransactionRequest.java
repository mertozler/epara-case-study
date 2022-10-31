package com.epara.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    @NotNull(message = "Base currency not to be null")
    private String baseCurrency;
    @NotEmpty(message = "Target currencies not be null")
    private List<String> targetCurrencies;

}
