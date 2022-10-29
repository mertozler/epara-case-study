package com.epara.epara.service;

import com.epara.epara.dto.TransactionDto;
import com.epara.epara.dto.converter.TransactionDtoConverter;
import com.epara.epara.exception.TransactionNotFoundException;
import com.epara.epara.model.Transaction;
import com.epara.epara.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        return converter.convertList(transactionRepository.findByCreationDateBetween(startDate,endDate));
    }

    public TransactionDto createTransaction(Transaction newTransaction){
        return converter.convert(transactionRepository.save(newTransaction));
    }
}
