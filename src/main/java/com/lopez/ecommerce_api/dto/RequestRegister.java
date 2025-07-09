package com.lopez.ecommerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegister {

    private String fullName;
    private String username;
    private String email;
    private String password;
}
