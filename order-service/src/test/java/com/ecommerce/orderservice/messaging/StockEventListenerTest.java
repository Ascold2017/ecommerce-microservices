package com.ecommerce.orderservice.messaging;

import com.ecommerce.core.command.ProcessPaymentCommand;
import com.ecommerce.core.event.StockReservationFailedEvent;
import com.ecommerce.core.event.StockReserved;
import com.ecommerce.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StockEventListenerTest {

    private OrderService orderService;
    private SagaCommandPublisher sagaCommandPublisher;
    private StockEventListener stockEventListener;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        sagaCommandPublisher = mock(SagaCommandPublisher.class);
        stockEventListener = new StockEventListener(orderService, sagaCommandPublisher);
    }

    @Test
    void testOnStockReservedPublishesPaymentCommand() {
        StockReserved event = new StockReserved(1L, 2L, 3L, 5, BigDecimal.valueOf(150.0));

        stockEventListener.onStockReserved(event);

        ArgumentCaptor<ProcessPaymentCommand> captor = ArgumentCaptor.forClass(ProcessPaymentCommand.class);
        verify(sagaCommandPublisher).publishProcessPayment(captor.capture());
        verifyNoInteractions(orderService);

        ProcessPaymentCommand command = captor.getValue();
        assertEquals(1L, command.orderId());
        assertEquals(2L, command.userId());
        assertEquals(BigDecimal.valueOf(150.0), command.amount());
    }

    @Test
    void testOnStockFailedCancelsOrder() {
        StockReservationFailedEvent event = new StockReservationFailedEvent(1L, 2L, "Out of stock");

        stockEventListener.onStockFailed(event);

        verify(orderService).cancelOrder(1L);
        verifyNoInteractions(sagaCommandPublisher);
    }
}
