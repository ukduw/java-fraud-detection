package com.ed.transaction_processing.common.event;

import com.ed.transaction_processing.common.model.TransactionStatus;

import java.time.Instant;

public class TransactionResultEvent {
    private String transactionId;
    private String userId;
    private TransactionStatus status; // APPROVED, FLAGGED, BLOCKED
    private int riskScore;
    private Instant processedAt;

    public TransactionResultEvent() {}

    public TransactionResultEvent(String transactionId, String userId, TransactionStatus status, int riskScore, Instant processedAt) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.status = status;
        this.riskScore = riskScore;
        this.processedAt = processedAt;
    }

    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public TransactionStatus getStatus() { return status; }
    public int getRiskScore() { return riskScore; }
    public Instant getProcessedAt() { return processedAt; }

    // are setters needed...?
}
