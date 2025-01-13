package com.example.cart_service.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "carts")
public class Cart {

    @Id
    private long id;
        private BigDecimal totalAmount = BigDecimal.ZERO;

    private Set<CartItem> items = new HashSet<>();

//    @OneToOne()
//    @JoinColumn(name="user_id")
//    private User user;
    private long userId;


    public void addItem(CartItem item) {
        this.items.add(item);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        updateTotalAmount();
    }

    public void updateTotalAmount() {
        this.totalAmount = items.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null) {
                return  BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<CartItem> getCartItemIfExist(long productId){
        return this.getItems().stream()
                .filter(item ->item.getProductId()==productId)
                .findFirst();
    }



}
