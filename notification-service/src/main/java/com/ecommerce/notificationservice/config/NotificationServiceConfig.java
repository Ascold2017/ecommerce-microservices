package com.ecommerce.notificationservice.config;

import com.ecommerce.notificationservice.centrifugo.CentrifugoClient;
import com.ecommerce.notificationservice.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yurii Miedviediev
 * @version 1.0.0
 * @since 11/07/2026
 */
@Configuration
public class NotificationServiceConfig {

    @Bean
    public NotificationService notificationService(CentrifugoClient centrifugoClient) {
        return new NotificationService(centrifugoClient);
    }

}
