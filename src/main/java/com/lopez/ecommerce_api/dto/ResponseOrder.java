package com.lopez.ecommerce_api.dto;

import com.lopez.ecommerce_api.model.CustomerOrder;

import java.util.List;

public record ResponseOrder(
        Long orderId,
        Double totalPrice,
        String orderStatus,
        String paymentStatus,
        List<OrderItem> orderItems
) {

    public static List<ResponseOrder> toListOfDto(List<CustomerOrder> customerOrders) {
        return customerOrders.stream()
                .map(order -> new ResponseOrder(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getOrderStatus().name(),
                        order.getPaymentStatus().name(),
                        order.getOrderItems().stream()
                                .map(orderItem -> new OrderItem(
                                        orderItem.getProduct().getName(),
                                        orderItem.getQuantity(),
                                        orderItem.getPrice()
                                )).toList()
                )).toList();
    }

    public static ResponseOrder toDto(CustomerOrder customerOrders) {
        return new ResponseOrder(
                customerOrders.getId(),
                customerOrders.getTotalPrice(),
                customerOrders.getOrderStatus().name(),
                customerOrders.getPaymentStatus().name(),
                customerOrders.getOrderItems().stream().map(orderItem -> new OrderItem(
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getPrice())).toList()
        );
    }



    public record OrderItem(
        String productName,
        Integer quantity,
        Double price
    ) {}
}
