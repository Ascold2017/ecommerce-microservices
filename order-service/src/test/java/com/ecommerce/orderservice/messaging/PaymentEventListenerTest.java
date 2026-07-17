package com.ecommerce.orderservice.messaging;

import com.ecommerce.core.command.ReleaseStockCommand;
import com.ecommerce.core.event.PaymentCompleted;
import com.ecommerce.core.event.PaymentFailed;
import com.ecommerce.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentEventListenerTest {

    private OrderService orderService;
    private SagaCommandPublisher sagaCommandPublisher;
    private PaymentEventListener paymentEventListener;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        sagaCommandPublisher = mock(SagaCommandPublisher.class);
        paymentEventListener = new PaymentEventListener(orderService, sagaCommandPublisher);
    }

    @Test
    void testOnPaymentCompletedConfirmsOrder() {
        PaymentCompleted event = new PaymentCompleted(1L, 2L, BigDecimal.valueOf(100.0));

        paymentEventListener.onPaymentCompleted(event);

        verify(orderService).confirmOrder(1L);
        verifyNoInteractions(sagaCommandPublisher);
    }

    @Test
    void testOnPaymentFailedCancelsOrderAndReleasesStock() {
        PaymentFailed event = new PaymentFailed(1L, 2L, "Insufficient funds");

        paymentEventListener.onPaymentFailed(event);

        verify(orderService).cancelOrder(1L);

        ArgumentCaptor<ReleaseStockCommand> captor = ArgumentCaptor.forClass(ReleaseStockCommand.class);
        verify(sagaCommandPublisher).publishReleaseStock(captor.capture());

        ReleaseStockCommand command = captor.getValue();
        assertEquals(1L, command.orderId());
        assertEquals(2L, command.userId());
        assertEquals("Insufficient funds", command.reason());
    }
}
