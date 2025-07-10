package com.fraud.springprac.api.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";

    // Modified constructor to accept both tokens
    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}