package com.ed.transaction.model;

import com.ed.transaction_processing.common.model.TransactionStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "transactions")
public class TransactionRecord {
    @Id
    private String transactionId;

    private String userId;
    private double qty;
    private String location;
    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private int riskScore;

    public TransactionRecord() {}

    // getters/setters...
    public String getTransactionId() { return transactionId; }
    public String getUserId() { return userId; }
    public double getQty() { return qty; }
    public String getLocation() { return location; }
    public Instant getTimestamp() { return timestamp; }
    public TransactionStatus getStatus() { return status; }
    public int getRiskScore() { return riskScore; }

    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setQty(double qty) { this.qty = qty; }
    public void setLocation(String location) { this.location = location; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }

}
