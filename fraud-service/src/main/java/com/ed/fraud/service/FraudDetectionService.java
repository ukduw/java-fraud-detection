package com.ed.fraud.service;

import com.ed.fraud.kafka.FraudResultProducer;
import com.ed.fraud.model.User;
import com.ed.fraud.model.UserTransaction;
import com.ed.fraud.repository.UserRepository;
import com.ed.fraud.repository.UserTransactionRepository;
import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.transaction_processing.common.event.TransactionResultEvent;
import com.ed.transaction_processing.common.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class FraudDetectionService {
    private final FraudResultProducer producer;
    private final UserRepository userRepo;
    private final UserTransactionRepository txRepo;

    public FraudDetectionService(FraudResultProducer producer, UserRepository userRepo, UserTransactionRepository txRepo) {
        this.producer = producer;
        this.userRepo = userRepo;
        this.txRepo = txRepo;
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
        txRepo.save(new UserTransaction(
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
        User user = userRepo.findById(event.getUserId()).orElse(null);
        if (user == null) {
            System.out.println("No such user");
            return -1;
        }


        int score = 0;

        // qty
        Object[] userTransactionStats = txRepo.findTransactionStatsByUserId(event.getUserId());
        Long transactionCount = (Long) userTransactionStats[0];
        Double avgSize = (Double) userTransactionStats[1];
        if (avgSize != null && avgSize > 0 && transactionCount >= 3) {
            double ratio = event.getQty() / avgSize;
            if (ratio >= 100) { // should probably use stdev instead...
                score += 70;
                System.out.println("Abnormal size detected: needs manual approval");
            } else if (ratio >= 10) {
                score += 40;
            }
            System.out.println("AVG: " + avgSize + ", CURRENT: " + event.getQty());
            }

        // location
        boolean unusualLocation = !event.getLocation().equalsIgnoreCase(user.getHomeLocation());
        if (unusualLocation) {
            score += 70;
            System.out.println("Unusual location detected: needs manual approval");
        }

        // high velocity
        List<UserTransaction> recent = txRepo.findTop5ByUserIdOrderByTimestampDesc(event.getUserId());
        boolean highVelocity = recent.stream().anyMatch(ut -> ut.getTimestamp().isAfter(event.getTimestamp().minusSeconds(5)));
        if (highVelocity) {
            score += 50;
            System.out.println("High transaction velocity detected: flagged");
        }


        return score;
    }

}
