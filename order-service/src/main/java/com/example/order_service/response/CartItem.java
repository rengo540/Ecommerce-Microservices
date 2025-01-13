package com.example.order_service.response;


import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private long productId;


    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(new BigDecimal(quantity));

    }


}