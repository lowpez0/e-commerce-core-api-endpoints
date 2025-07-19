package com.lopez.ecommerce_api.dto;

import com.lopez.ecommerce_api.model.Cart;

import java.util.ArrayList;
import java.util.List;

public record ResponseCart(
        Integer cartId,
        List<Items> items
) {

   //convert entity to dto
   public static ResponseCart toDto(Cart cart) {
       if(cart.getCartItems().isEmpty()) {
           return new ResponseCart(cart.getId(), new ArrayList<>());
       }
       List<Items> list = cart.getCartItems().stream()
               .map(cartItem -> new Items(
                      cartItem.getId(),
                      cartItem.getProduct().getName(),
                      cartItem.getProduct().getPrice(),
                      cartItem.getQuantity()
              )).toList();
       return new ResponseCart(cart.getId(), list);
   }

   public record Items(
        Long cartItemId,
        String name,
        Double price,
        Integer quantity

   ) {}
}
