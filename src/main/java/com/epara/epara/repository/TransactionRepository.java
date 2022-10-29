package com.epara.epara.repository;

import com.epara.epara.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);
}
