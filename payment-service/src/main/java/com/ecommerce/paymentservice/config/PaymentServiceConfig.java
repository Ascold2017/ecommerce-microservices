package com.ecommerce.paymentservice.config;

import com.ecommerce.paymentservice.messaging.PaymentEventPublisher;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.DefaultPaymentService;
import com.ecommerce.paymentservice.service.LocalPaymentService;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Yurii Miedviediev
 * @version 1.0.0
 * @since 11/07/2026
 */
@Configuration
public class PaymentServiceConfig {

    @Bean
    @Profile("!local")
    PaymentService defaultPaymentService(PaymentRepository paymentRepository,
                                         PaymentEventPublisher eventPublisher) {
        return new DefaultPaymentService(paymentRepository, eventPublisher);
    }

    @Bean
    @Profile("local")
    PaymentService localPaymentService() {
        return new LocalPaymentService();
    }
}
