package com.lopez.ecommerce_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRegister {

    @JsonIgnore
    private String error;
    private String accessToken;
    private String refreshToken;
    private boolean registered;
}
