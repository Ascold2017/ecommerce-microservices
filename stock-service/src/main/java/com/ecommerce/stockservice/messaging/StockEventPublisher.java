package com.ecommerce.stockservice.messaging;

import com.ecommerce.stockservice.event.OrderCreatedEvent;
import com.ecommerce.stockservice.event.StockReserved;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisher {

    public static final String TOPIC = "stock-events";

    private final KafkaTemplate<String, StockReserved> kafkaTemplate;

    public StockEventPublisher(KafkaTemplate<String, StockReserved> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStockReserved(StockReserved event) {
        // ключ = userId → все события одного юзера попадут в одну партицию
        kafkaTemplate.send(TOPIC, String.valueOf(event.userId()), event);
    }
}