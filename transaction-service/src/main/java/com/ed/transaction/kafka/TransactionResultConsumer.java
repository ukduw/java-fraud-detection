package com.ed.transaction.kafka;

import com.ed.transaction.model.TransactionRecord;
import com.ed.transaction.repository.TransactionRepository;
import com.ed.transaction_processing.common.event.TransactionResultEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionResultConsumer {
    private final TransactionRepository repo;

    public TransactionResultConsumer(TransactionRepository repo) {
        this.repo = repo;
    }

    @KafkaListener(topics = "transaction-results", groupId = "transaction-group")
    public void consume(TransactionResultEvent event) {
        TransactionRecord record = new TransactionRecord();

        record.setTransactionId(event.getTransactionId());
        record.setUserId(event.getUserId());
        record.setQty(event.getQty());
        record.setLocation(event.getLocation());
        record.setTimestamp(event.getProcessedAt());
        record.setStatus(event.getStatus());
        record.setRiskScore(event.getRiskScore());

        repo.save(record);
    }
}
