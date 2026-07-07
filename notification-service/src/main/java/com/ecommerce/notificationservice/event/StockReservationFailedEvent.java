package com.ecommerce.notificationservice.event;

public record StockReservationFailedEvent(Long orderId, Long userId, String reason) {
}
