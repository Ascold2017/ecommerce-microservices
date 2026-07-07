package com.ecommerce.stockservice.event;

public record StockReservationFailed(Long orderId, Long userId, String reason) {
}

