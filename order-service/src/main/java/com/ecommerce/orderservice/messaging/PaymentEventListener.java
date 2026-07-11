package com.ecommerce.orderservice.messaging;

import com.ecommerce.core.event.PaymentCompleted;
import com.ecommerce.core.event.PaymentFailed;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);

    private final OrderService orderService;

    @KafkaListener(topics = "payment-events")
    public void onPaymentCompleted(PaymentCompleted event) {
        log.info("📩 Получено событие о заказе: orderId={}, userId={}, amount={}",
                event.orderId(), event.userId(), event.amount());
        orderService.confirmOrder(event.orderId());
    }

    @KafkaListener(topics = "payment-failed-events")
    public void onPaymentFailed(PaymentFailed event) {
        log.info("📩 Получено событие о неудачном платеже: orderId={}, userId={}, reason={}",
                event.orderId(), event.userId(), event.reason());
        orderService.cancelOrder(event.orderId());
    }
}