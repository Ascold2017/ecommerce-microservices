package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.centrifugo.CentrifugoClient;
import com.ecommerce.notificationservice.event.OrderCreatedEvent;
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
        String text = "🛒 Ваш заказ создан: " + event.productName()
                + " (x" + event.quantity() + "), номер #" + event.orderId();

        centrifugoClient.publish(CHANNEL_PREFIX + event.userId(), Map.of("message", text));
    }


}