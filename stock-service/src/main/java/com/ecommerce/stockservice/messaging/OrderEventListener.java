package com.ecommerce.stockservice.messaging;

import com.ecommerce.core.event.OrderCreatedEvent;
import com.ecommerce.core.event.PaymentFailed;
import com.ecommerce.stockservice.service.StockReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final StockReservationService stockReservationService;

    public OrderEventListener(StockReservationService stockReservationService) {
        this.stockReservationService = stockReservationService;
    }

    @KafkaListener(topics = "order-events")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("📩 Получено событие о заказе: orderId={}, userId={}, productId={}, qty={}",
                event.orderId(), event.userId(), event.productId(), event.quantity());
        stockReservationService.reserve(event);
    }


    @KafkaListener(topics = "payment-failed-events")
    public void onPaymentFailed(PaymentFailed event) {
        log.info("📩 Получено событие о неуспешной оплате: orderId={}", event.orderId());
        stockReservationService.release(event);
    }
}