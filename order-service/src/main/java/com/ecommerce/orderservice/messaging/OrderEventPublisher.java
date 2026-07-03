package com.ecommerce.orderservice.messaging;

import com.ecommerce.orderservice.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    public static final String TOPIC = "order-events";

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        // ключ = userId → все события одного юзера попадут в одну партицию
        kafkaTemplate.send(TOPIC, String.valueOf(event.userId()), event);
    }
}
