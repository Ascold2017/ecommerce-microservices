package com.ecommerce.orderservice.event;

import com.ecommerce.orderservice.model.Order;

import java.time.Instant;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Long productId,
        Integer quantity,
        Instant createdAt
) {

    public static OrderCreatedEvent fromOrder(Order order) {
        return new OrderCreatedEvent(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getQuantity(),
                order.getCreatedAt()
        );
    }
}
