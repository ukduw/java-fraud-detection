package com.ed.fraud.service;

import com.ed.transaction_processing.common.event.TransactionEvent;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {
    public void checkFraud(TransactionEvent event) {
        if (event.getQty() > 10000) {
            System.out.println("Fraud detected: " + event.getTransactionId());
        } else {
            System.out.println("Transaction valid: " + event.getTransactionId());
        }
    }

}
