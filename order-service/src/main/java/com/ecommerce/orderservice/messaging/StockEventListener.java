package com.ecommerce.orderservice.messaging;

import com.ecommerce.core.command.ProcessPaymentCommand;
import com.ecommerce.core.event.StockReservationFailedEvent;
import com.ecommerce.core.event.StockReserved;
import com.ecommerce.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StockEventListener {

    private static final Logger log = LoggerFactory.getLogger(StockEventListener.class);

    private final OrderService orderService;
    private final SagaCommandPublisher sagaCommandPublisher;

    public StockEventListener(OrderService orderService, SagaCommandPublisher sagaCommandPublisher) {
        this.orderService = orderService;
        this.sagaCommandPublisher = sagaCommandPublisher;
    }

    @KafkaListener(topics = "stock-events")
    public void onStockReserved(StockReserved event) {
        log.info("📩 [Orchestrator] Получено событие об успешной резервации: orderId={}, userId={}, amount={}",
                event.orderId(), event.userId(), event.amount());
        ProcessPaymentCommand command = new ProcessPaymentCommand(event.orderId(), event.userId(), event.amount());
        sagaCommandPublisher.publishProcessPayment(command);
        log.info("📤 [Orchestrator] Отправлена команда на оплату: orderId={}", event.orderId());
    }

    @KafkaListener(topics = "stock-failed-events")
    public void onStockFailed(StockReservationFailedEvent event) {
        log.info("📩 Получено событие о неудачной резервации: orderId={}, userId={}, reason={}",
                event.orderId(), event.userId(), event.reason());
        orderService.cancelOrder(event.orderId());
    }
}
