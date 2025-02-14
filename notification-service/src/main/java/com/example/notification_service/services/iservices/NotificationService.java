package com.example.notification_service.services.iservices;

import com.example.notification_service.events.OrderUpdateEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService{

    @Override
    @KafkaListener(topics = "order-updates", groupId = "notification-group")
    public void handleOrderUpdate(OrderUpdateEvent event) {
        // Business logic to send notifications
        System.out.println("Received order update: " + event);

        // Example: Send an email or push notification
        sendNotification(event.getUserId(), "Your order " + event.getOrderId() + " is now " + event.getStatus());
    }

    private void sendNotification(long userId, String message) {
        // Logic to send notification
        System.out.println("Sending notification to user " + userId + ": " + message);
    }
}
