package com.ed.fraud.kafka;

import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.fraud.service.FraudDetectionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {
    private final FraudDetectionService fraudService;

    public TransactionConsumer(FraudDetectionService fraudService) {
        this.fraudService = fraudService;
    }

    @KafkaListener(topics="transactions", groupId="fraud-group")
    public void consume(TransactionEvent event) {
        fraudService.checkFraud(event);
    }

}
