package com.ecommerce.paymentservice.messaging;

import com.ecommerce.core.command.ProcessPaymentCommand;
import com.ecommerce.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class PaymentCommandListenerTest {

    private PaymentService paymentService;
    private PaymentCommandListener paymentCommandListener;

    @BeforeEach
    void setUp() {
        paymentService = mock(PaymentService.class);
        paymentCommandListener = new PaymentCommandListener(paymentService);
    }

    @Test
    void testOnProcessPaymentCallsService() {
        ProcessPaymentCommand command = new ProcessPaymentCommand(1L, 2L, BigDecimal.valueOf(100.0));

        paymentCommandListener.onProcessPayment(command);

        verify(paymentService).process(command);
    }
}
