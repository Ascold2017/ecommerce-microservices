package com.ecommerce.stockservice.messaging;

import com.ecommerce.stockservice.event.StockReservationFailed;
import com.ecommerce.stockservice.event.StockReserved;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisher {

    private final KafkaTemplate<String, StockReserved> kafkaTemplate;
    private final KafkaTemplate<String, StockReservationFailed> failedEventsKafkaTemplate;

    public StockEventPublisher(KafkaTemplate<String, StockReserved> kafkaTemplate,
                                KafkaTemplate<String, StockReservationFailed> failedEventsKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.failedEventsKafkaTemplate = failedEventsKafkaTemplate;
    }

    public void publishStockReserved(StockReserved event) {
        kafkaTemplate.send("stock-events", String.valueOf(event.userId()), event);
    }

    public void publishStockReservationFailed(StockReservationFailed event) {
        failedEventsKafkaTemplate.send("stock-failed-events", String.valueOf(event.userId()), event);
    }
}