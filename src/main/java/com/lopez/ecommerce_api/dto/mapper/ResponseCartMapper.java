package com.lopez.ecommerce_api.dto.mapper;

import com.lopez.ecommerce_api.dto.ResponseCart;
import com.lopez.ecommerce_api.model.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

//alternative for entityFrom() in record ResponseCart
public class ResponseCartMapper implements Function<Cart, ResponseCart> {

    @Override
    public ResponseCart apply(Cart cart) {
        if(cart.getCartItems().isEmpty()) {
            return new ResponseCart(cart.getId(), new ArrayList<>());
        }
        List<ResponseCart.Items> list = cart.getCartItems().stream()
                .map(cartItem -> new ResponseCart.Items(
                        cartItem.getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity()
                )).toList();
        return new ResponseCart(cart.getId(), list);
    }
}
