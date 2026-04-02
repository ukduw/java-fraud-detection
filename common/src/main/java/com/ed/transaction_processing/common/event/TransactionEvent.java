package com.ed.transaction_processing.common.event;

public class TransactionEvent {
	private String transactionId;
	private String userId;
	private double qty;

	public TransactionEvent() {}

	public TransactionEvent(String transactionId, String userId, double qty) {
		this.transactionId = transactionId;
		this.userId = userId;
		this.qty = qty;
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


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
}
