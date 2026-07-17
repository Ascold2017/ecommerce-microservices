package com.ecommerce.paymentservice.service;

import com.ecommerce.core.command.ProcessPaymentCommand;
import com.ecommerce.core.event.PaymentCompleted;
import com.ecommerce.core.event.PaymentFailed;
import com.ecommerce.paymentservice.messaging.PaymentEventPublisher;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentStatus;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class DefaultPaymentService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    @Value("${payment.decline-threshold}")
    private int declineThreshold;

    @Transactional
    public void process(ProcessPaymentCommand command) {
        boolean declined = command.amount().compareTo(BigDecimal.valueOf(declineThreshold)) >= 0;  // из @Value

        Payment payment = new Payment();
        payment.setOrderId(command.orderId());
        payment.setAmount(command.amount());
        payment.setStatus(declined ? PaymentStatus.FAILED : PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        if (declined) {
            eventPublisher.publishPaymentFailed(new PaymentFailed(
                    command.orderId(), command.userId(), "Оплата отклонена: сумма " + command.amount()));
        } else {
            eventPublisher.publishPaymentCompleted(new PaymentCompleted(
                    command.orderId(), command.userId(), command.amount()));
        }
    }
}