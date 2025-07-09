package com.lopez.ecommerce_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAuthentication {

    private String accessToken;
    private String refreshToken;
    private String error;
    private boolean success;
}
