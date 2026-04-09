package com.ed.notification.kafka;

import com.ed.notification.service.NotificationService;
import com.ed.transaction_processing.common.event.TransactionResultEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResultConsumer {
    private final NotificationService service;

    public ResultConsumer(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "transaction-results", groupId = "notification-group")
    public void consume(TransactionResultEvent event) {
        service.handleResult(event);
    }
}
