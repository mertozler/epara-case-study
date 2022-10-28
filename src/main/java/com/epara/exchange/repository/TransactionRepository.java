package com.epara.exchange.repository;

import com.epara.exchange.dto.TransactionDto;
import com.epara.exchange.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);
}
