package com.epara.exchange.dto.converter;

import com.epara.exchange.dto.TransactionDto;
import com.epara.exchange.model.Transaction;
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

    public List<TransactionDto> convertList(List<Transaction> from){
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for(var item: from){
            transactionDtoList.add(TransactionDto.builder()
                            .id(item.getId()).
                            base(item.getBase()).
                            rates(item.getRates()).build());
        }
        return transactionDtoList;
    }
}
