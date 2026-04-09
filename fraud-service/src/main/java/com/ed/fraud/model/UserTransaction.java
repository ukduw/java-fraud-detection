package com.ed.fraud.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private double qty;
    private String location;
    private Instant timestamp;

    public UserTransaction() {}

    public UserTransaction(String userId, double qty, String location, Instant timestamp) {
        this.userId = userId;
        this.qty = qty;
        this.location = location;
        this.timestamp = timestamp;
    }

    // needs getters/setters
    public String getLocation() { return location; }
    public Instant getTimestamp() { return timestamp; }
}
