package com.ed.payment.controller;

import com.ed.transaction_processing.common.event.TransactionEvent;
import com.ed.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public String createPayment(@RequestBody TransactionEvent event) {
        paymentService.processPayment(event);
        return "Payment sent";
    }


}
