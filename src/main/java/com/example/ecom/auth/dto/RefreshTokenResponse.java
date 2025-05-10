package com.example.ecom.auth.dto;

import lombok.Data;

@Data
public class RefreshTokenResponse {
  private String refreshToken;
  private String accessToken;
  private String type = "Bearer";

  public RefreshTokenResponse(String refreshToken, String accessToken) {
    this.refreshToken = refreshToken;
    this.accessToken = accessToken;
  }
}
