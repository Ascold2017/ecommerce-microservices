package com.ecommerce.notificationservice.messaging;

import com.ecommerce.notificationservice.event.*;
import com.ecommerce.notificationservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "order-events")
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("📩 Получено событие о заказе: orderId={}, userId={}, product={}, qty={}",
                event.orderId(), event.userId(), event.productId(), event.quantity());
        notificationService.notifyOrderCreated(event);
    }

    @KafkaListener(topics = "payment-events")
    public void onPaymentCompleted(PaymentCompleted event) {
        log.info("📩 Получено событие о оплате: orderId={}, userId={}, amount={}",
                event.orderId(), event.userId(), event.amount());
        notificationService.notifyPaymentCompleted(event);
    }

    @KafkaListener(topics = "stock-events")
    public void onStockReserved(StockReserved event) {
        log.info("📩 Получено событие о резервировании: orderId={}, userId={}, productId={}, qty={}, amount={}",
                event.orderId(), event.userId(), event.productId(), event.quantity(), event.amount());
        notificationService.notifyStockReserved(event);
    }

    @KafkaListener(topics = "stock-failed-events")
    public void onStockReservationFailed(StockReservationFailedEvent event) {
        log.info("📩 Получено событие о неудачном резервировании: orderId={}, userId={}, reason={}",
                event.orderId(), event.userId(), event.reason());
        notificationService.notifyStockReservationFailed(event);
    }

    @KafkaListener(topics = "payment-failed-events")
    public void onPaymentFailed(PaymentFailed event) {
        log.info("📩 Получено событие о неудачной оплате: orderId={}, userId={}, reason={}",
                event.orderId(), event.userId(), event.reason());
        notificationService.notifyPaymentFailed(event);
    }


}