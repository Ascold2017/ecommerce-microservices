package com.ecommerce.core.command;

import java.math.BigDecimal;

public record ProcessPaymentCommand(
        Long orderId,
        Long userId,
        BigDecimal amount
) {}
