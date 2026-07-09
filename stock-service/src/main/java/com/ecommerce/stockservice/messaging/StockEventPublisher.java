package com.ecommerce.stockservice.messaging;

import com.ecommerce.core.event.StockReservationFailedEvent;
import com.ecommerce.core.event.StockReserved;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisher {

    private final KafkaTemplate<String, StockReserved> kafkaTemplate;
    private final KafkaTemplate<String, StockReservationFailedEvent> failedEventsKafkaTemplate;

    public StockEventPublisher(KafkaTemplate<String, StockReserved> kafkaTemplate,
                                KafkaTemplate<String, StockReservationFailedEvent> failedEventsKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.failedEventsKafkaTemplate = failedEventsKafkaTemplate;
    }

    public void publishStockReserved(StockReserved event) {
        kafkaTemplate.send("stock-events", String.valueOf(event.userId()), event);
    }

    public void publishStockReservationFailed(StockReservationFailedEvent event) {
        failedEventsKafkaTemplate.send("stock-failed-events", String.valueOf(event.userId()), event);
    }
}