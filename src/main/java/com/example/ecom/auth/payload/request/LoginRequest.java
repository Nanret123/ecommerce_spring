package com.example.ecom.auth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "LoginRequest", description = "Request payload for user authentication")
public class LoginRequest {

    @Schema(description = "User's username", example = "janedoe", required = true)
    @NotBlank
    private String username;

    @Schema(description = "User's password", example = "StrongPass!456", required = true)
    @NotBlank
    private String password;
}