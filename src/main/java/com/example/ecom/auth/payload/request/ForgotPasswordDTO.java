package com.example.ecom.auth.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDTO {
  @Schema(description = "Email of the user", example = "johnDoe@gmail.com")
  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;
}
