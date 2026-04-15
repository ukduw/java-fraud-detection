package com.ed.fraud.service;

import com.ed.fraud.kafka.FraudResultProducer;
import com.ed.fraud.model.User;
import com.ed.fraud.model.UserTransaction;
import com.ed.fraud.repository.UserRepository;
import com.ed.fraud.repository.UserTransactionRepository;
import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.transaction_processing.common.event.TransactionResultEvent;
import com.ed.transaction_processing.common.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(FraudDetectionService.class);


    public void checkFraud(TransactionEvent event) {
        int score = calculateRiskScore(event);

        TransactionStatus status = TransactionStatus.PENDING; // never saved to db...
        if (score >= 70) {
            status = TransactionStatus.BLOCKED;
        } else if (score >= 50) {
            status = TransactionStatus.FLAGGED;
        } else {
            status = TransactionStatus.APPROVED;
        }

        if (score == -1) {
            status = TransactionStatus.BLOCKED;
            log.info("No such user userId={} transactionStatus={}",
                    event.getUserId(), event.getTransactionStatus());
        }


        // save transaction
        txRepo.save(new UserTransaction(
            event.getTransactionId(),
            event.getUserId(),
            event.getQty(),
            event.getLocation(),
            event.getTimestamp(),
            status,
            score
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
        log.info("Processing transactionId={} userId={} location={} qty={} timestamp={}",
                event.getTransactionId(), event.getUserId(), event.getLocation(), event.getQty(), event.getTimestamp());

        User user = userRepo.findById(event.getUserId()).orElse(null);
        if (user == null) {
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
                log.info("Abnormal size detected: needs manual approval transactionId={} userId={} avgQty={} currentQty={} timestamp={}",
                        event.getTransactionStatus(), event.getUserId(), avgSize, event.getQty(), event.getTimestamp());
            } else if (ratio >= 10) {
                score += 40;
            }
            }

        // location
        boolean unusualLocation = !event.getLocation().equalsIgnoreCase(user.getHomeLocation());
        if (unusualLocation) {
            score += 70;
            log.info("Unusual location detected: needs manual approval transactionId={} userId={} usualLocation={} currentLocation={} qty={} timestamp={}",
                    event.getTransactionId(), event.getUserId(), user.getHomeLocation(), event.getLocation(), event.getQty(), event.getTimestamp());
        }

        // high velocity
        List<UserTransaction> recent = txRepo.findTop5ByUserIdOrderByTimestampDesc(event.getUserId());
        boolean highVelocity = recent.stream().anyMatch(ut -> ut.getTimestamp().isAfter(event.getTimestamp().minusSeconds(5)));
        if (highVelocity) {
            score += 50;
            log.info("High transaction velocity detected: flagged - ending transaction transactionId={} userId={} location={} qty={} timestamp={}",
                    event.getTransactionId(), event.getUserId(), event.getLocation(), event.getQty(), event.getTimestamp());
        }


        return score;
    }

}
