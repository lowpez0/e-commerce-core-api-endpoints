package com.lopez.ecommerce_api.controller;

import com.lopez.ecommerce_api.dto.ResponseCart;
import com.lopez.ecommerce_api.dto.ResponseOrder;
import com.lopez.ecommerce_api.service.order_service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<?> convertCartToOrder(Principal principal) {
        orderService.saveOrder(principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ResponseOrder>> getUserOrders(Principal principal) {
        return new ResponseEntity<>(orderService.getUserOrders(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrder> getUserOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getUserOrder(id), HttpStatus.OK);
    }
}
