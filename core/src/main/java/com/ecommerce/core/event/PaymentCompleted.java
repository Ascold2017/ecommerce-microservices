package com.ecommerce.core.event;

import java.math.BigDecimal;

public record PaymentCompleted(Long orderId, Long userId, BigDecimal amount) {
}
