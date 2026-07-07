package com.ecommerce.notificationservice.event;

import java.time.Instant;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Long productId,
        Integer quantity,
        Instant createdAt
) {}