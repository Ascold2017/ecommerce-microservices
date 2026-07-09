package com.ecommerce.core.event;

public record PaymentFailed(Long orderId, Long userId, String reason) {
}
