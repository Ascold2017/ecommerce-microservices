package com.ecommerce.paymentservice.service;

import com.ecommerce.core.command.ProcessPaymentCommand;

/**
 * @author Yurii Miedviediev
 * @version 1.0.0
 * @since 11/07/2026
 */
public interface PaymentService {
    void process(ProcessPaymentCommand command);
}
