package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.centrifugo.CentrifugoClient;
import com.ecommerce.notificationservice.event.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

    public static final String CHANNEL_PREFIX = "notifications#";

    private final CentrifugoClient centrifugoClient;

    public NotificationService(CentrifugoClient centrifugoClient) {
        this.centrifugoClient = centrifugoClient;
    }

    public void notifyOrderCreated(OrderCreatedEvent event) {
        String text = "🛒 Ваш заказ создан: #" + event.productId()
                + " (x" + event.quantity() + "), номер #" + event.orderId();

        centrifugoClient.publish(CHANNEL_PREFIX + event.userId(), Map.of("message", text));
    }

    public void notifyPaymentCompleted(PaymentCompleted event) {
        String text = "💳 Оплата заказа #" + event.orderId() + " прошла успешно";

        centrifugoClient.publish(CHANNEL_PREFIX + event.userId(), Map.of("message", text));
    }

    public void notifyStockReserved(StockReserved event) {
        String text = "📦 Товар #" + event.productId() + " успешно зарезервирован";

        centrifugoClient.publish(CHANNEL_PREFIX + event.userId(), Map.of("message", text));
    }

    public void notifyPaymentFailed(PaymentFailed event) {
        String text = "💳 Оплата заказа #" + event.orderId() + " не удалась: " + event.reason();

        centrifugoClient.publish(CHANNEL_PREFIX + event.userId(), Map.of("message", text));
    }

    public void notifyStockReservationFailed(StockReservationFailedEvent event) {
        String text = "📦 Резервирование заказа #" + event.orderId() + " не удалось: " + event.reason();

        centrifugoClient.publish(CHANNEL_PREFIX + event.userId(), Map.of("message", text));
    }



}