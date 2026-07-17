package com.ecommerce.paymentservice.service;

import com.ecommerce.core.command.ProcessPaymentCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LocalPaymentService implements PaymentService {

    public void process(ProcessPaymentCommand command) {
        log.info("payment processing");
    }
}