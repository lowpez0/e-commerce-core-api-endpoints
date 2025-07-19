package com.lopez.ecommerce_api.dto;


public record RequestRegister(

        String fullName,
        String username,
        String email,
        String password) {

}
