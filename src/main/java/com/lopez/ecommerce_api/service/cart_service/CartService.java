package com.lopez.ecommerce_api.service.cart_service;

import com.lopez.ecommerce_api.dto.RequestCart;
import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.dto.ResponseCart;
import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.model.CartItem;

public interface CartService {

    boolean addProductToCart(RequestProduct request, String username);
    void addCartToUser(String username);
    ResponseCart getCart(String username);
    Cart getCartByUsername(String username);
    void deleteCartItem(Long id);
    void updateCartItems(RequestCart requestCart);
}
