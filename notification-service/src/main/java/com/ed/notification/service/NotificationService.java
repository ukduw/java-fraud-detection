package com.ed.notification.service;

import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.transaction_processing.common.event.TransactionResultEvent;
import com.ed.transaction_processing.common.model.TransactionStatus;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void handleResult(TransactionResultEvent event) {
        switch (event.getStatus()) {
            case TransactionStatus.APPROVED:
                System.out.println("Payment approved for " + event.getUserId());
                break;
            case TransactionStatus.FLAGGED:
                System.out.println("Flagged transaction for " + event.getUserId());
                break;
            case TransactionStatus.BLOCKED:
                System.out.println("Blocked transaction for " + event.getUserId());
        }
    }
}

// update to structured json logs ("event", "transactionId", "userId", "riskScore")