package com.ecommerce.orderservice.event;

public record StockReservationFailedEvent(Long orderId, Long userId, String reason) {
}

