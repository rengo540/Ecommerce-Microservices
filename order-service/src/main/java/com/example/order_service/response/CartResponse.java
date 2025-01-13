package com.example.order_service.response;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    @Id
    private long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItem> items = new HashSet<>();
    private long userId;
}
