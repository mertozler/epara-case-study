package com.epara.service;

import com.epara.dto.TransactionDto;
import com.epara.dto.converter.TransactionDtoConverter;
import com.epara.exception.TransactionListIsEmptyException;
import com.epara.exception.TransactionNotFoundException;
import com.epara.model.Transaction;
import com.epara.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionDtoConverter converter;

    public TransactionService(TransactionRepository transactionRepository, TransactionDtoConverter converter) {
        this.transactionRepository = transactionRepository;
        this.converter = converter;
    }

    public TransactionDto findTransactionById(String id){
        return converter.convert(transactionRepository.findById(id).orElseThrow(
                () -> new TransactionNotFoundException("Transaction not found")
        ));
    }

    public  List<TransactionDto> findTransactionByDateRange(LocalDate startDate, LocalDate endDate){
        List<Transaction> transactionList = transactionRepository.findByCreationDateBetween(startDate,endDate);

        List<TransactionDto> transactionDtoList= transactionList.stream()
                .map(converter::convert)
                .collect(Collectors.toList());

        if(transactionDtoList.isEmpty()){
            throw new TransactionListIsEmptyException("No transaction data can be found in this date range. " +
                    "Please check the date range you entered.");
        }
        return transactionDtoList;
    }

    public TransactionDto createTransaction(Transaction newTransaction){
        return converter.convert(transactionRepository.save(newTransaction));
    }
}
