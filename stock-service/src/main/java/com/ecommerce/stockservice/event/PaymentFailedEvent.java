package com.ecommerce.stockservice.event;

import java.math.BigDecimal;

public record PaymentFailedEvent(Long orderId, Long userId, BigDecimal amount) {
}
