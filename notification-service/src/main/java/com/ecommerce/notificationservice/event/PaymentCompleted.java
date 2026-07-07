package com.ecommerce.notificationservice.event;

import java.math.BigDecimal;

public record PaymentCompleted(Long orderId, Long userId, BigDecimal amount) {
}
