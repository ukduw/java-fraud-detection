package com.ed.payment.service;

import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.payment.kafka.TransactionProducer;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final TransactionProducer producer;

    public PaymentService(TransactionProducer producer) {
        this.producer = producer;
    }

    public void processPayment(TransactionEvent event) {
        producer.sendTransaction(event);
    }

}
