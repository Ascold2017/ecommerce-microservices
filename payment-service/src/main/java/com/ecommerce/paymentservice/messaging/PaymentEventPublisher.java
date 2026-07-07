package com.ecommerce.paymentservice.messaging;

import com.ecommerce.paymentservice.event.PaymentCompleted;
import com.ecommerce.paymentservice.event.PaymentFailed;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {


    private final KafkaTemplate<String, PaymentCompleted> kafkaTemplate;
    private final KafkaTemplate<String, PaymentFailed> failedEventsTemplate;

    public PaymentEventPublisher(KafkaTemplate<String, PaymentCompleted> kafkaTemplate, KafkaTemplate<String, PaymentFailed> failedEventsTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.failedEventsTemplate = failedEventsTemplate;
    }

    public void publishPaymentCompleted(PaymentCompleted event) {
        // ключ = userId → все события одного юзера попадут в одну партицию
        kafkaTemplate.send("payment-events", String.valueOf(event.userId()), event);
    }

    public void publishPaymentFailed(PaymentFailed event) {
        failedEventsTemplate.send("payment-failed-events", String.valueOf(event.userId()), event);
    }
}
