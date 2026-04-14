package com.ed.fraud.service;

import com.ed.fraud.kafka.FraudResultProducer;
import com.ed.fraud.model.UserTransaction;
import com.ed.fraud.repository.UserTransactionRepository;
import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.transaction_processing.common.event.TransactionResultEvent;
import com.ed.transaction_processing.common.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FraudDetectionService {
    private final UserTransactionRepository repo;
    private final FraudResultProducer producer;

    public FraudDetectionService(UserTransactionRepository repo, FraudResultProducer producer) {
        this.repo = repo;
        this.producer = producer;
    }

    public void checkFraud(TransactionEvent event) {
        int score = calculateRiskScore(event);

        TransactionStatus status = TransactionStatus.APPROVED;
        if (score >= 50 && score <= 69) {
            status = TransactionStatus.FLAGGED;
        }
        if (score >= 70) {
            status = TransactionStatus.BLOCKED;
        }

        // save transaction
        repo.save(new UserTransaction(
            event.getUserId(),
            event.getQty(),
            event.getLocation(),
            event.getTimestamp(),
            event.getTransactionStatus()
        ));

        TransactionResultEvent result = new TransactionResultEvent(
            event.getTransactionId(),
            event.getUserId(),
            status,
            score,
            Instant.now()
        );

        producer.sendResult(result);
    }

    private int calculateRiskScore(TransactionEvent event) {
        int score = 0;

        if (event.getQty() > 10000) {   // should query db for average qty; if different by order of magnitude, block
            score += 70;
        }

        List<UserTransaction> recent = repo.findTop5ByUserIdOrderByTimestampDesc(event.getUserId());
        boolean newLocation = recent.stream().noneMatch(ut -> ut.getLocation().equals(event.getLocation()));
        if (newLocation && !recent.isEmpty()) {
            score += 70;
        }

        boolean highVelocity = recent.stream().anyMatch(ut -> ut.getTimestamp().isAfter(event.getTimestamp().minusSeconds(5)));
        if (highVelocity) {
            score += 50;
        }


        return score;
    }

}
