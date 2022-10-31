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

    public List<TransactionDto> convertList(List<Transaction> from){
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for(var transaction: from){
            transactionDtoList.add(TransactionDto.builder()
                    .id(transaction.getId()).
                    base(transaction.getBase()).
                    rates(transaction.getRates()).build());
        }
        return transactionDtoList;
    }
}
