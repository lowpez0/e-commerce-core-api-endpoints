package com.lopez.ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(Integer id, User user) {
        this.id = id;
        this.user = user;
    }

    public static List<OrderItem> fromEntity(Cart cart) {
        List<OrderItem> itemList = new ArrayList<>();

        return null;
    }
}
