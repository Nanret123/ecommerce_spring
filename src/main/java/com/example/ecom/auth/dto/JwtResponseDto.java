package com.example.ecom.auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
  private String token;
  private String refreshToken;
  private String type = "Bearer";
  private UUID id;
  private String username;
  private String email;
  private String role;

  public JwtResponseDto(String token, String refreshToken, UUID id, String username, String email, String role) {
    this.token = token;
    this.refreshToken = refreshToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.role = role;
    this.type = "Bearer"; // set default manually
}
}
