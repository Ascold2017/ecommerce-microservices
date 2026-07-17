package com.ecommerce.core.command;

public record ReleaseStockCommand(
        Long orderId,
        Long userId,
        String reason
) {}
