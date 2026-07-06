package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.event.PaymentCompleted;
import com.ecommerce.paymentservice.event.StockReserved;
import com.ecommerce.paymentservice.messaging.PaymentEventPublisher;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentStatus;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void process(StockReserved event) {
        // эмуляция: в 3b оплата всегда успешна; ветку отказа добавим в 3c
        Payment payment = new Payment();
        payment.setOrderId(event.orderId());
        payment.setAmount(event.amount());
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        eventPublisher.publishPaymentCompleted(
                new PaymentCompleted(event.orderId(), event.userId(), event.amount()));
    }
}