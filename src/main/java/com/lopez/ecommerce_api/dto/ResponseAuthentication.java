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
public class ResponseAuthentication {

    private String accessToken;
    private String refreshToken;
    @JsonIgnore
    private String error;
    private boolean success;
}
