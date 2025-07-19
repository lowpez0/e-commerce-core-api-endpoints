package com.lopez.ecommerce_api.dto;

import java.util.List;

public record RequestCart(
        List<CartItems> cart
) {
    public record CartItems(
            Long id,
            Integer quantity
    ) {

    }
}
