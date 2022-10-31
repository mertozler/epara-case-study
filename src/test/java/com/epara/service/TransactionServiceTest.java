package com.epara.service;

import com.epara.dto.TransactionDto;
import com.epara.dto.converter.TransactionDtoConverter;
import com.epara.exception.TransactionListIsEmptyException;
import com.epara.exception.TransactionNotFoundException;
import com.epara.model.Transaction;
import com.epara.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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
                base("TRY").
                rates(rates).
                build();

        when(transactionRepository.findById("test")).thenReturn(Optional.of(transaction));
        when(converter.convert(transaction)).thenReturn(transactionDto);
        //when

        TransactionDto result = service.findTransactionById("test");

        //then
        assertEquals(result, transactionDto);
    }

    @Test
    public void findTransactionById_whenCustomerIdDoesNotExist_shouldReturnTransactionNotFoundException(){

       when(transactionRepository.findById("id")).thenReturn(Optional.empty());

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
                base("TRY").
                rates(rates).
                build();

        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(converter.convert(transaction)).thenReturn(transactionDto);
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

        when(transactionRepository.findByCreationDateBetween(startDate,endDate)).thenReturn(transactionList);
        //when

        List<TransactionDto> result = service.findTransactionByDateRange(startDate,endDate);

        //then
        assertEquals(result.size(), transactionDtoList.size());
    }

    @Test
    public void findTransactionByDateRange_WhenThereIsNoTransactionData_ShouldReturnEmptyList(){
        //given
        LocalDate startDate =  LocalDate.of(2022, 10, 8);
        LocalDate endDate = LocalDate.now();
        String exceptionMessage = "No transaction data can be found in this date range. Please check the date range you entered.";

        List<Transaction> transactionList = new ArrayList<>();


        when(transactionRepository.findByCreationDateBetween(startDate,endDate)).thenReturn(transactionList);
        //when

        TransactionListIsEmptyException exception = assertThrows(TransactionListIsEmptyException.class,
                () -> service.findTransactionByDateRange(startDate,endDate));


        //then
        assertEquals(exception.getMessage(), exceptionMessage);
    }


}