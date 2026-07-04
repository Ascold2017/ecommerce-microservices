package com.ecommerce.notificationservice.event;

import java.time.Instant;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        String productName,
        Integer quantity,
        Instant createdAt
) {}