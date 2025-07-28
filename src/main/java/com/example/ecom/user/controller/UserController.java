package com.example.ecom.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.dto.UserDto;
import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.user.dto.UpdateProfileDTO;
import com.example.ecom.user.dto.UserUpdateDTO;
import com.example.ecom.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
@SecurityRequirement(name = "apiBearerAuth")
public class UserController {

  private final UserService userService;

  @GetMapping()
  @Operation(summary = "Get all users (Admins Only)", description = "Retrieves a list of all registered users")
  @PreAuthorize("hasRole('ADMIN')")
  public Result<List<UserDto>> getAllUsers() {
    return Result.success("User list retrieved successfully", userService.getAllUsers());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get user by ID (Admins Only)", description = "Retrieves user details by user ID")
  @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
  public Result<UserDto> getUserById(@PathVariable UUID id) {
    return Result.success("User retrieved successfully", userService.getUserById(id));
  }

  @GetMapping("/me")
  @Operation(summary = "Get current user", description = "Retrieves the details of the currently authenticated user")
  public Result<UserDto> getCurrentUser() {
    return Result.success("Current user retrieved successfully", userService.getCurrentUser());
  }

  @PutMapping("/me")
  @Operation(summary = "Update current user profile", description = "Update the currently authenticated user's profile")
  public Result<UserDto> updateCurrentUserProfile(@Valid @RequestBody UpdateProfileDTO userDto) {
    return Result.success("User profile updated successfully", userService.updateProfile(userDto));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update user by ID (Admins Only)", description = "Update user details by user ID")
  @PreAuthorize("hasRole('ADMIN')")
  public Result<UserDto> updateUserById(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO userDto) {
    return Result.success("User updated successfully", userService.updateUser(id, userDto));
  }

  @Operation(summary = "Delete user by ID (Admins Only)", description = "Delete a user by their ID")
  @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
  @DeleteMapping("/{id}")
  public Result<Void> deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
    return Result.success("User deleted successfully", null);
  }
}
