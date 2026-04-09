package com.ed.fraud.service;

import com.ed.fraud.model.UserTransaction;
import com.ed.fraud.repository.UserTransactionRepository;
import com.ed.transaction_processing.common.event.TransactionEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudDetectionService {
    private final UserTransactionRepository repo;

    public FraudDetectionService(UserTransactionRepository repo) {
        this.repo = repo;
    }

    public void checkFraud(TransactionEvent event) {
        if (event.getQty() > 10000) {
            System.out.println("Fraud detected: " + event.getTransactionId());
        } else {
            System.out.println("Transaction valid: " + event.getTransactionId());
        }


        List<UserTransaction> recent = repo.findTop5ByUserIdOrderByTimestampDesc(event.getUserId());

        boolean newLocation = recent.stream().noneMatch(ut -> ut.getLocation().equals(event.getLocation()));

        if (newLocation && !recent.isEmpty()) {
            System.out.println("Fraud: new location for user " + event.getUserId());
        }


        boolean highVelocity = recent.stream().anyMatch(ut -> ut.getTimestamp().isAfter(event.getTimestamp().minusSeconds(10)));
        if (highVelocity) {
            System.out.println("Fraud: high velocity transactions " + event.getUserId());
        }

        // save transaction
        repo.save(new UserTransaction(
            event.getUserId(),
            event.getQty(),
            event.getLocation(),
            event.getTimestamp()
        ));
    }

}
