package com.ed.notification.kafka;

import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private final NotificationService service;

    public NotificationConsumer(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics="transactions", groupId="notification-group")
    public void consume(TransactionEvent event) {
        service.sendNotification(event);
    }

}
