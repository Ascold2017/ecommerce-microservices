package com.ecommerce.paymentservice.service;

import com.ecommerce.core.event.PaymentCompleted;
import com.ecommerce.core.event.PaymentFailed;
import com.ecommerce.core.event.StockReserved;
import com.ecommerce.paymentservice.messaging.PaymentEventPublisher;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentStatus;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    @Value("${payment.decline-threshold}")
    private int declineThreshold;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void process(StockReserved event) {
        boolean declined = event.amount().compareTo(BigDecimal.valueOf(declineThreshold)) >= 0;  // из @Value

        Payment payment = new Payment();
        payment.setOrderId(event.orderId());
        payment.setAmount(event.amount());
        payment.setStatus(declined ? PaymentStatus.FAILED : PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        if (declined) {
            eventPublisher.publishPaymentFailed(new PaymentFailed(
                    event.orderId(), event.userId(), "Оплата отклонена: сумма " + event.amount()));
        } else {
            eventPublisher.publishPaymentCompleted(new PaymentCompleted(
                    event.orderId(), event.userId(), event.amount()));
        }
    }
}