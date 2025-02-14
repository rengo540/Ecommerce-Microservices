package com.example.notification_service.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderUpdateEvent {
    private String orderId;
    private String status;
    private long userId;
}
