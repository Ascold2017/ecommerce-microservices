package com.ecommerce.orderservice.event;

import java.math.BigDecimal;

public record PaymentFailedEvent(Long orderId, Long userId, String reason) {
}
