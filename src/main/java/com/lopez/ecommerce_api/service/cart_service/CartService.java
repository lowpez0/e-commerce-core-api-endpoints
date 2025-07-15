package com.lopez.ecommerce_api.service.cart_service;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.model.Cart;

public interface CartService {

    boolean addProductToCart(RequestProduct request, String username);
    void addCartToUser(String username);
    Cart getCart(String username);
    void deleteCartItem(Long id);
}
