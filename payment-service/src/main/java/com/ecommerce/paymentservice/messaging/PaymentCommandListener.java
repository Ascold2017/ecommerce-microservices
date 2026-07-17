package com.ecommerce.paymentservice.messaging;

import com.ecommerce.core.command.ProcessPaymentCommand;
import com.ecommerce.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentCommandListener.class);
    private final PaymentService paymentService;

    public PaymentCommandListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "payment-commands")
    public void onProcessPayment(ProcessPaymentCommand command) {
        log.info("📩 Получена команда на оплату: orderId={}, userId={}, amount={}",
                command.orderId(), command.userId(), command.amount());
        paymentService.process(command);
    }
}
