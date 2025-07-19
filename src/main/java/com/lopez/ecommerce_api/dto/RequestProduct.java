package com.lopez.ecommerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RequestProduct {

    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
    private int quantity;

    public RequestProduct(String name, String description, double price, int stockQuantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
}
