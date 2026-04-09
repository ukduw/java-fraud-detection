package com.ed.transaction_processing.common.event;

import java.time.Instant;

public class TransactionEvent {
	private String transactionId;
	private String userId;
	private double qty;
	private String location;
	private Instant timestamp;

	public TransactionEvent() {}

	public TransactionEvent(String transactionId, String userId, double qty, String location, Instant timestamp) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.qty = qty;
		this.location = location;
		this.timestamp = timestamp;
	}


	public String getTransactionId() {
		return transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public double getQty() {
		return qty;
	}
	public String getLocation() { return location; }
	public Instant getTimestamp() { return timestamp; }
		// note: use in FraudDetectionService logic

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public void setLocation(String location) { this.location = location; }
	public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
		// in what scenarios does transaction timestamp need to be changed...?
}
