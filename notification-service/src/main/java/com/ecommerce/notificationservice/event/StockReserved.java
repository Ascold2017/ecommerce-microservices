package com.ecommerce.notificationservice.event;

import java.math.BigDecimal;

public record StockReserved(Long orderId, Long userId, Long productId, Integer quantity, BigDecimal amount) {
}