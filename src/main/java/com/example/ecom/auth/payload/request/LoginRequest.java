package com.example.ecom.auth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "LoginRequest", description = "Request payload for user authentication")
public class LoginRequest {
    
    @Schema(description = "User's username", example = "johndoe", required = true)
    @NotBlank
    private String username;
    
    @Schema(description = "User's password", example = "password123", required = true)
    @NotBlank
    private String password;
}