package com.ecommerce.core.event;

public record StockReservationFailedEvent(Long orderId, Long userId, String reason) {
}
