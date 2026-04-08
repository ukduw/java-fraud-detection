package com.ed.fraud.repository;

import com.ed.fraud.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
    List<UserTransaction> findTop5ByUserIdOrderByTimestampDesc(String userId);
}
