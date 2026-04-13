package com.ed.transaction.repository;

import com.ed.transaction.model.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionRecord, String> {
    List<TransactionRecord> findByUserId(String userId);
}
