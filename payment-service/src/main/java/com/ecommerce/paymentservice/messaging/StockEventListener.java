package com.ecommerce.paymentservice.messaging;

import com.ecommerce.core.event.StockReserved;
import com.ecommerce.paymentservice.service.DefaultPaymentService;
import com.ecommerce.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StockEventListener {

    private static final Logger log = LoggerFactory.getLogger(StockEventListener.class);
    private final PaymentService paymentService;

    public StockEventListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @KafkaListener(topics = "stock-events")
    public void onOrderCreated(StockReserved event) {
        log.info("📩 Получено событие о заказе: orderId={}, userId={}, productId={}, qty={}",
                event.orderId(), event.userId(), event.productId(), event.quantity());
        paymentService.process(event);
    }
}
