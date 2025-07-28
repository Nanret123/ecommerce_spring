package com.example.ecom.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.dto.JwtResponseDto;
import com.example.ecom.auth.dto.RefreshTokenRequest;
import com.example.ecom.auth.dto.RefreshTokenResponse;
import com.example.ecom.auth.dto.UserDto;
import com.example.ecom.auth.payload.request.ForgotPasswordDTO;
import com.example.ecom.auth.payload.request.LoginRequest;
import com.example.ecom.auth.payload.request.ResetPasswordDTO;
import com.example.ecom.auth.payload.request.SignUpRequest;
import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.auth.service.AuthService;
import com.example.ecom.auth.service.RefreshTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication related APIs")
public class AuthController {

  private final AuthService authService;
  private final RefreshTokenService tokenService;

  public AuthController(AuthService authService, RefreshTokenService tokenService) {
    this.authService = authService;
    this.tokenService = tokenService;
  }

  @Operation(
    summary = "Register a new user",
    description = "Creates a new user account with the provided details"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200", 
      description = "User registered successfully",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
      ),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "409", description = "Username/email already exists")
    })
  @PostMapping("/signup")
  public Result<UserDto> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    UserDto user = authService.registerUser(signUpRequest);
    return Result.success("User registered successfully", user);
  }

  @Operation(
    summary = "Authenticate user",
    description = "Logs in a user and returns JWT tokens"
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", 
        description = "User logged in successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
        ),
      @ApiResponse(responseCode = "401", description = "Invalid credentials"),
      @ApiResponse(responseCode = "404", description = "User not found")
    })
  @PostMapping("/signin")
  public Result<JwtResponseDto> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
    JwtResponseDto response = authService.authenticateUser(loginRequest);
    return Result.success("User logged in successfully", response);
  }

  @Operation(
    summary = "Refresh JWT token",
    description = "Creates a new access token using a valid refresh token"
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", 
        description = "Refresh token created successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
        ),
      @ApiResponse(responseCode = "401", description = "Invalid refresh token"),
      @ApiResponse(responseCode = "403", description = "Refresh token expired")
    })
  @PostMapping("/refreshToken")
  public Result<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
     RefreshTokenResponse response = tokenService.handleRefreshToken(request.getRefreshToken());
     return Result.success("Refresh token created successfully", response);
  }

  @Operation(
    summary = "Sign out user",
    description = "Logs out the currently authenticated user",
    security = @SecurityRequirement(name = "apiBearerAuth")
    )
    @ApiResponses(value = {
      @ApiResponse(
        responseCode = "200", 
        description = "Log out successful",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
      ),
      @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
  @PostMapping("/signout")
  public Result<Void> sigout(){
     authService.logoutUser();
    return Result.success("User logged out successfully", null);
  }

  @PostMapping("/forgot-password")
  @Operation(
    summary = "Forgot password",
    description = "Sends a password reset token to the user's email"
    )
  public Result<Void> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
    authService.forgotPassword(forgotPasswordDTO);
    return Result.success("Password reset token sent successfully", null);
  }

  @PostMapping("/reset-password")
  @Operation(
    summary = "Reset password",
    description = "Resets the user's password using the provided token"
    )
  public Result<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
    authService.resetPassword(resetPasswordDTO);
    return Result.success("Password reset successfully", null);
  } 
}
