package com.ecommerce.paymentservice.event;

import java.math.BigDecimal;

public record PaymentCompleted(Long orderId, Long userId, BigDecimal amount) {
}
