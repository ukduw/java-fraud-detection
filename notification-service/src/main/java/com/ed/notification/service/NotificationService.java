package com.ed.notification.service;

import com.ed.transaction_processing.common.event.TransactionEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendNotification(TransactionEvent event) {
        System.out.println("Notifying user: " + event.getUserId());
    }
}
