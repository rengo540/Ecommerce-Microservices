package com.example.notification_service.events;

import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer implements Deserializer<OrderUpdateEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OrderUpdateEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, OrderUpdateEvent.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize OrderUpdatesResponse", e);
        }
    }
}