package com.ed.payment.service;

import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.payment.kafka.TransactionProducer;
import com.ed.transaction_processing.common.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentService {
    private final TransactionProducer producer;

    public PaymentService(TransactionProducer producer) {
        this.producer = producer;
    }

    public void processPayment(TransactionEvent event) {
        event.setTimestamp(Instant.now());
            // timestamp should be set in producer (not controller, consumer...)
        event.setStatus(TransactionStatus.PENDING);
        producer.sendTransaction(event);
    }

}
