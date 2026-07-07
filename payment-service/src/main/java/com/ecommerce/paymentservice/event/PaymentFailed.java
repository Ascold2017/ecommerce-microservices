package com.ecommerce.paymentservice.event;

import java.math.BigDecimal;

public record PaymentFailed(Long orderId, Long userId, String reason) {
}

