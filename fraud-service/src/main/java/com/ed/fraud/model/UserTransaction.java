package com.ed.fraud.model;

import com.ed.transaction_processing.common.model.TransactionStatus;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_transactions")
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private double qty;
    private String location;
    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public UserTransaction() {}

    public UserTransaction(String userId, double qty, String location, Instant timestamp, TransactionStatus status) {
        this.userId = userId;
        this.qty = qty;
        this.location = location;
        this.timestamp = timestamp;
        this.status = status;
    }

    // needs getters/setters
    public String getLocation() { return location; }
    public Instant getTimestamp() { return timestamp; }
}
