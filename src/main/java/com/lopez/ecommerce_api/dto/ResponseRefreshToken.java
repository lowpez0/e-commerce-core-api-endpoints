package com.lopez.ecommerce_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseRefreshToken {

    private String accessToken;
    @JsonIgnore
    private String error;
    private boolean valid;
}
