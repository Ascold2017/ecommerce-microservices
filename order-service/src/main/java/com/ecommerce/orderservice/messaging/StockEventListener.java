package com.ecommerce.orderservice.messaging;

import com.ecommerce.orderservice.event.StockReservationFailedEvent;
import com.ecommerce.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StockEventListener {

    private static final Logger log = LoggerFactory.getLogger(StockEventListener.class);

    private final OrderService orderService;

    public StockEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "stock-failed-events")
    public void onStockFailed(StockReservationFailedEvent event) {
        log.info("📩 Получено событие о неудачной резервации: orderId={}, userId={}, reason={}",
                event.orderId(), event.userId(), event.reason());
        orderService.cancelOrder(event.orderId());
    }
}
