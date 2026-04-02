package com.ed.payment.kafka;

import com.ed.transaction_processing.common.event.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(TransactionEvent event) {
        kafkaTemplate.send("transactions", event.getTransactionId(), event);
    }


}
