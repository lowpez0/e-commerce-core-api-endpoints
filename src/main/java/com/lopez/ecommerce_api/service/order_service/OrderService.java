package com.lopez.ecommerce_api.service.order_service;

import com.lopez.ecommerce_api.dto.ResponseOrder;

import java.security.Principal;
import java.util.List;

public interface OrderService {

    void saveOrder(Principal principal);
    List<ResponseOrder> getUserOrders(String username);
    ResponseOrder getUserOrder(Long id);

}
