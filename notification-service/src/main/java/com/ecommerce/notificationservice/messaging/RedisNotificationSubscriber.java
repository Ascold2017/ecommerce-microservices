package com.ecommerce.notificationservice.messaging;

import com.ecommerce.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RedisNotificationSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    public RedisNotificationSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8); // notifications:1
        String text = new String(message.getBody(), StandardCharsets.UTF_8);       // текст

        String userId = channel.substring(NotificationService.CHANNEL_PREFIX.length());

        // доставляем локально; сработает только если сокет этого юзера на ЭТОМ инстансе
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", text);
    }
}