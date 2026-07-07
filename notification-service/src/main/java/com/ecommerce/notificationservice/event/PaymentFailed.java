package com.ecommerce.notificationservice.event;

public record PaymentFailed(Long orderId, Long userId, String reason) {
}
