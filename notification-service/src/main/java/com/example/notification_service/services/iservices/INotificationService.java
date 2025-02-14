package com.example.notification_service.services.iservices;

import com.example.notification_service.events.OrderUpdateEvent;

public interface INotificationService {

    public void handleOrderUpdate(OrderUpdateEvent event);
}
