package com.ed.fraud.kafka;

import com.ed.transaction_processing.common.event.TransactionResultEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FraudResultProducer {
    private final KafkaTemplate<String, TransactionResultEvent> kafkaTemplate;

    public FraudResultProducer(KafkaTemplate<String, TransactionResultEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResult(TransactionResultEvent event) {
        kafkaTemplate.send("transaction-results", event.getTransactionId(), event);
    }
}
