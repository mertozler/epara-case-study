package com.epara.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TransactionDto implements Serializable {
    private String id;
    private String base;
    private Map<String, Double> rates;
}
