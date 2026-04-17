package com.ed.fraud.repository;

import com.ed.fraud.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
    List<UserTransaction> findTop5ByUserIdOrderByTimestampDesc(String userId);

    @Query("SELECT COUNT(t), AVG(t.qty) FROM UserTransaction t WHERE t.userId = :userId")
    List<Object[]> findTransactionStatsByUserId(@Param("userId") String userId);
}
