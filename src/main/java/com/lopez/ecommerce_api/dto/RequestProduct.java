package com.lopez.ecommerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProduct {

    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;
}
