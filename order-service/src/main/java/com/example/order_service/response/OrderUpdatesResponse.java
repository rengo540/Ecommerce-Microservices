package com.example.order_service.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class OrderUpdatesResponse {
    private String orderId;
    private String status;
    private long userId;
}
