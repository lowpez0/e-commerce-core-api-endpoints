package com.lopez.ecommerce_api.controller;

import com.lopez.ecommerce_api.dto.RequestCart;
import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.dto.ResponseCart;
import com.lopez.ecommerce_api.service.cart_service.CartService;
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
    public ResponseEntity<ResponseCart> getCart(Principal principal) {
        ResponseCart responseCart = cartService.getCart(principal.getName());
        return new ResponseEntity<>(responseCart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addProductToCart(@RequestBody RequestProduct product,
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

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItems(@RequestBody RequestCart requestCart) {
        cartService.updateCartItems(requestCart);
        return new ResponseEntity<>(Map.of("message:", "Updated successfully"), HttpStatus.OK);
    }

}
