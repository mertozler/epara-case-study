package com.epara.epara.service;

import com.epara.epara.dto.TransactionDto;
import com.epara.epara.dto.converter.TransactionDtoConverter;
import com.epara.epara.exception.TransactionNotFoundException;
import com.epara.epara.model.Transaction;
import com.epara.epara.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


public class TransactionServiceTest {

    private TransactionService service;
    private TransactionRepository transactionRepository;
    private TransactionDtoConverter converter;

    @BeforeEach
    public void setUp(){
        transactionRepository = mock(TransactionRepository.class);
        converter = mock(TransactionDtoConverter.class);
        service = new TransactionService(transactionRepository, converter);
    }

    @Test
    public void findTransactionById_whenCustomerIdExists_shouldReturnCustomer(){
        //given
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("TRY", 18.0);
        Transaction transaction = new Transaction("test", LocalDate.now(),true,"TRY",rates);

        TransactionDto transactionDto = TransactionDto.
                builder().
                id("test").
                base("USD").
                rates(rates).
                build();

        Mockito.when(transactionRepository.findById("test")).thenReturn(Optional.of(transaction));
        Mockito.when(converter.convert(transaction)).thenReturn(transactionDto);
        //when

        TransactionDto result = service.findTransactionById("test");

        //then
        assertEquals(result, transactionDto);
    }

    @Test
    public void findTransactionById_whenCustomerIdDoesNotExist_shouldReturnTransactionNotFoundException(){

       Mockito.when(transactionRepository.findById("id")).thenReturn(Optional.empty());

       assertThrows(TransactionNotFoundException.class, () -> service.findTransactionById("id"));
    }

    @Test
    public void createTransaction_WhenTransactionIsNotNull_ShouldReturnTransaction(){
        //given
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("TRY", 18.0);
        Transaction transaction = new Transaction("test", LocalDate.now(),true,"TRY",rates);

        TransactionDto transactionDto = TransactionDto.
                builder().
                id("test").
                base("USD").
                rates(rates).
                build();

        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Mockito.when(converter.convert(transaction)).thenReturn(transactionDto);
        //when

        TransactionDto result = service.createTransaction(transaction);

        //then
        assertEquals(result, transactionDto);
    }

    @Test
    public void findTransactionByDateRange_WhenDateRangeIsCorrect_ShouldReturnTransactionList(){
        //given
        LocalDate startDate =  LocalDate.of(2022, 10, 8);
        LocalDate endDate = LocalDate.now();

        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("TRY", 18.0);
        Transaction transaction = new Transaction("test", LocalDate.now(),true,"TRY",rates);

        TransactionDto transactionDto = TransactionDto.
                builder().
                id("test").
                base("USD").
                rates(rates).
                build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        List<TransactionDto> transactionDtoList = new ArrayList<>();
        transactionDtoList.add(transactionDto);

        Mockito.when(transactionRepository.findByCreationDateBetween(startDate,endDate)).thenReturn(transactionList);
        Mockito.when(converter.convertList(transactionList)).thenReturn(transactionDtoList);
        //when

        List<TransactionDto> result = service.findTransactionByDateRange(startDate,endDate);

        //then
        assertEquals(result, transactionDtoList);
    }


}