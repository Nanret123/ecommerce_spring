package com.example.ecom.auth.payload.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "SignUpRequest", description = "Request payload for user registration")
public class SignUpRequest {
    
    @Schema(description = "User's unique username", example = "johndoe", required = true)
    @NotBlank
    @Size(min = 3, max = 20)
    String username;
    
    @Schema(description = "User's first name", example = "John", required = true)
    @NotBlank
    String firstName;
    
    @Schema(description = "User's last name", example = "Doe", required = true)
    @NotBlank
    String lastName;
    
    @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
    @NotBlank
    @Size(max = 50)
    @Email
    String email;
    
    @Schema(description = "User's password", example = "password123", required = true, minLength = 6, maxLength = 40)
    @NotBlank
    @Size(min = 6, max = 40)
    String password;
    
    @Schema(description = "User's role (optional)", example = "USER", allowableValues = {"USER", "ADMIN"})
    String role;
}
