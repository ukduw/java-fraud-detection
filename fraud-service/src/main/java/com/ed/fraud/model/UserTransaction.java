package com.ed.fraud.model;

import com.ed.transaction_processing.common.model.TransactionStatus;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_transactions")
public class UserTransaction {
    @Id
    private String transactionId;
    private String userId;
    private double qty;
    private String location;
    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private int riskScore;

    public UserTransaction() {}

    public UserTransaction(String transactionId, String userId, double qty, String location, Instant timestamp, TransactionStatus status, int riskScore) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.qty = qty;
        this.location = location;
        this.timestamp = timestamp;
        this.status = status;
        this.riskScore = riskScore;
    }

    // needs getters/setters
    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public double getQty() { return qty; }
    public String getLocation() { return location; }
    public Instant getTimestamp() { return timestamp; }
    public TransactionStatus getStatus() { return status; }
    public int getRiskScore() { return riskScore; }

    // need setters...?
}
