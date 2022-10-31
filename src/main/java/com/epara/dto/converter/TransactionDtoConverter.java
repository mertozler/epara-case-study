package com.epara.dto.converter;

import com.epara.dto.TransactionDto;
import com.epara.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction from){
        return TransactionDto.builder()
                .id(from.getId()).
                base(from.getBase()).
                rates(from.getRates()).build();
    }

}
