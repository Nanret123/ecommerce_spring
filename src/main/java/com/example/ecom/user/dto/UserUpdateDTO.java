package com.example.ecom.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO{
  @Schema(description = "User's username", example = "johndoe")
  private String username;

  @Schema(description = "User's first name", example = "John")
  private String firstName;

  @Schema(description = "User's last name", example = "Doe")
  private String lastName;

  @Schema(description = "User's email", example = "johnDoe@gmail.com")
  @Email(message = "Email should be valid")
  private String email;

  @Schema(description = "User's role (admin only)")
  private String role;

  @Schema(description = "User's active status(admin only)", example = "true")
  private Boolean active;
}
