package com.ecommerce.stockservice.messaging;

import com.ecommerce.core.command.ReleaseStockCommand;
import com.ecommerce.core.event.OrderCreatedEvent;
import com.ecommerce.stockservice.service.StockReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.Mockito.*;

class OrderEventListenerTest {

    private StockReservationService stockReservationService;
    private OrderEventListener orderEventListener;

    @BeforeEach
    void setUp() {
        stockReservationService = mock(StockReservationService.class);
        orderEventListener = new OrderEventListener(stockReservationService);
    }

    @Test
    void testOnOrderCreatedCallsService() {
        OrderCreatedEvent event = new OrderCreatedEvent(1L, 2L, 3L, 5, Instant.now());

        orderEventListener.onOrderCreated(event);

        verify(stockReservationService).reserve(event);
    }

    @Test
    void testOnReleaseStockCallsService() {
        ReleaseStockCommand command = new ReleaseStockCommand(1L, 2L, "Compensation");

        orderEventListener.onReleaseStock(command);

        verify(stockReservationService).release(command);
    }
}
