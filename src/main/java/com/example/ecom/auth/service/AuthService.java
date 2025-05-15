package com.example.ecom.auth.service;


import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecom.auth.dto.JwtResponseDto;
import com.example.ecom.auth.dto.UserDto;
import com.example.ecom.auth.payload.request.LoginRequest;
import com.example.ecom.auth.payload.request.SignUpRequest;
import com.example.ecom.model.ERole;
import com.example.ecom.model.RefreshToken;
import com.example.ecom.model.Role;
import com.example.ecom.model.User;
import com.example.ecom.repository.RoleRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.security.jwt.JwtService;
import com.example.ecom.security.services.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtService jwtUtils;
  private final RefreshTokenService refreshTokenService;

  public AuthService(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder encoder,
      JwtService jwtUtils,
      RefreshTokenService refreshTokenService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.encoder = encoder;
    this.jwtUtils = jwtUtils;
    this.refreshTokenService = refreshTokenService;
  }

  // sign up method
  public UserDto registerUser(SignUpRequest signUpRequest) {
     // Normalize and trim input
     String username = signUpRequest.getUsername().trim();
     String email = signUpRequest.getEmail().toLowerCase().trim();

    if (userRepository.existsByUsername(username)) {
       throw new IllegalArgumentException("Username is already taken");
    }
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email is already in use");
    }
    // create new user
    User user = new User();
    user.setUsername(signUpRequest.getUsername());
    user.setEmail(signUpRequest.getEmail());
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));

    String roleStr = signUpRequest.getRole();
    ERole enumRole = ERole.USER; // default

    if (roleStr != null && !roleStr.isEmpty()) {
      try {
          enumRole = ERole.valueOf(roleStr.toUpperCase());
      } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Invalid role: " + roleStr);
      }
  }

    // fetch role from the db
    Role role = roleRepository.findByName(enumRole)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

    user.setRole(role);
    User savedUser = userRepository.save(user);

    return new UserDto(
        savedUser.getId(),
        savedUser.getUsername(),
        savedUser.getFirstName(),
        savedUser.getLastName(),
        savedUser.getEmail(),
        savedUser.getRole().getName().name(),
        savedUser.getActive(),
        savedUser.getCreatedAt(),
        savedUser.getUpdatedAt()
        );

  }

  // login method
  public JwtResponseDto authenticateUser(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(authentication);

    String role = userDetails.getAuthorities()
        .stream()
        .findFirst()
        .map(item -> item.getAuthority())
        .orElse("");

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return new JwtResponseDto(
        jwt,
        refreshToken.getToken(),
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        role     
    );
  }

  //logout
 public void logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
    }

}
