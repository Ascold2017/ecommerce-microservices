package com.ecommerce.orderservice.messaging;

import com.ecommerce.core.command.ProcessPaymentCommand;
import com.ecommerce.core.command.ReleaseStockCommand;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SagaCommandPublisher {

    public static final String PAYMENT_COMMANDS_TOPIC = "payment-commands";
    public static final String STOCK_COMMANDS_TOPIC = "stock-commands";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SagaCommandPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProcessPayment(ProcessPaymentCommand command) {
        kafkaTemplate.send(PAYMENT_COMMANDS_TOPIC, String.valueOf(command.userId()), command);
    }

    public void publishReleaseStock(ReleaseStockCommand command) {
        kafkaTemplate.send(STOCK_COMMANDS_TOPIC, String.valueOf(command.orderId()), command);
    }
}
