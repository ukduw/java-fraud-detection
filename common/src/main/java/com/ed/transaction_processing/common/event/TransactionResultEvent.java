package com.ed.transaction_processing.common.event;

import com.ed.transaction_processing.common.model.TransactionStatus;

import java.time.Instant;

public class TransactionResultEvent {
    private String transactionId;
    private String userId;
    private Double qty;
    private String location;
    private Instant processedAt;
    private TransactionStatus status; // APPROVED, FLAGGED, BLOCKED
    private int riskScore;

    public TransactionResultEvent() {}

    public TransactionResultEvent(String transactionId, String userId, Double qty, String location, Instant processedAt, TransactionStatus status, int riskScore) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.qty = qty;
        this.location = location;
        this.processedAt = processedAt;
        this.status = status;
        this.riskScore = riskScore;
    }

    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public Double getQty() { return qty; }
    public String getLocation() { return location; }
    public Instant getProcessedAt() { return processedAt; }
    public TransactionStatus getStatus() { return status; }
    public int getRiskScore() { return riskScore; }

    // are setters needed...?
}
