package com.ed.transaction_processing.common.event;

public class TransactionEvent {
	private String transactionId;
	private String userId;
	private double qty;
	private String location;

	public TransactionEvent() {}

	public TransactionEvent(String transactionId, String userId, double qty, String location) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.qty = qty;
		this.location = location;
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
}
