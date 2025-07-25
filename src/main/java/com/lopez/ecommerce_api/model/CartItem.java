package com.lopez.ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lopez.ecommerce_api.enums.CartItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private CartItemStatus status;
}
