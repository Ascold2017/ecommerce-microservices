package com.ecommerce.paymentservice.service;

import com.ecommerce.core.event.StockReserved;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LocalPaymentService implements PaymentService {

    public void process(StockReserved event) {
        log.info("payment processing");
    }
}