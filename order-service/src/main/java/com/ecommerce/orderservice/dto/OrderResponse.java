package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.model.Order;

import java.time.Instant;

public record OrderResponse(
        Long id,
        Long productId,
        Integer quantity,
        String status,
        Instant createdAt
) {

    public static OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}
