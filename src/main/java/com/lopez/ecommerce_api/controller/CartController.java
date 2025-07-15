package com.lopez.ecommerce_api.controller;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.dto.ResponseCart;
import com.lopez.ecommerce_api.model.Cart;
import com.lopez.ecommerce_api.service.cart_service.CartService;
import com.lopez.ecommerce_api.service.user_service.UserService;
import com.lopez.ecommerce_api.service.product_service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {
        Cart cart = cartService.getCart(principal.getName());
        return new ResponseEntity<>(ResponseCart.builder().price(cart.getTotalPrice()).build(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestBody RequestProduct product,
                                              Principal principal) {
        boolean ifAddedToCart = cartService.addProductToCart(product, principal.getName());
        if(!ifAddedToCart) {
            return new ResponseEntity<>(Map.of("error", "Product is out of stuck"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(Map.of("message", "Added to cart"), HttpStatus.OK);
    }

    @DeleteMapping("/itemId/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
