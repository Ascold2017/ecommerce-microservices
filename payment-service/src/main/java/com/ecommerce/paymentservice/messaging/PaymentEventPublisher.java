package com.ecommerce.paymentservice.messaging;

import com.ecommerce.paymentservice.event.PaymentCompleted;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    public static final String TOPIC = "payment-events";

    private final KafkaTemplate<String, PaymentCompleted> kafkaTemplate;

    public PaymentEventPublisher(KafkaTemplate<String, PaymentCompleted> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCompleted(PaymentCompleted event) {
        // ключ = userId → все события одного юзера попадут в одну партицию
        kafkaTemplate.send(TOPIC, String.valueOf(event.userId()), event);
    }
}
