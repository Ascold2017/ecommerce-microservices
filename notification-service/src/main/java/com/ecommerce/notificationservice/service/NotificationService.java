package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.event.OrderCreatedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public static final String CHANNEL_PREFIX = "notifications:";
    private final StringRedisTemplate redisTemplate;

    public NotificationService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void notifyOrderCreated(OrderCreatedEvent event) {
        String text = "🛒 Ваш заказ создан: " + event.productName()
                + " (x" + event.quantity() + "), номер #" + event.orderId();
        redisTemplate.convertAndSend(CHANNEL_PREFIX + event.userId(), text);
    }
}