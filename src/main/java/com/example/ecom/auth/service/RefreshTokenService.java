package com.example.ecom.auth.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.ecom.auth.dto.RefreshTokenResponse;
import com.example.ecom.exception.token.RefreshTokenException;
import com.example.ecom.model.RefreshToken;
import com.example.ecom.model.User;
import com.example.ecom.repository.RefreshTokenRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.security.jwt.JwtUtils;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
  @Value("${security.jwt.refresh.expiration}")
  private Long refreshTokenDurationMs;

  private final RefreshTokenRepository refreshTokenRepo;
  private final UserRepository userRepo;
  private final JwtUtils jwtUtils;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepo,
                             UserRepository userRepo, JwtUtils jwtUtils) {
    this.refreshTokenRepo = refreshTokenRepo;
    this.userRepo = userRepo;
    this.jwtUtils = jwtUtils;
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepo.findByToken(token);
  }

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();
    // find user
    User user = userRepo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    // delete token if it exists
    Optional<RefreshToken> existingToken = refreshTokenRepo.findByUser(user);
    existingToken.ifPresent(refreshTokenRepo::delete);

    refreshToken.setUser(user);
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken = refreshTokenRepo.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepo.delete(token);
      throw new RuntimeException("Refresh token was expired. Please make a new sign in request");
    }
    return token;
  }

  @Transactional
  public void deleteByUserId(Long userId) {
    User user = userRepo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    refreshTokenRepo.deleteByUser(user);
  }

  public RefreshTokenResponse handleRefreshToken(String token) {
    return findByToken(token)
        .map(this::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String newToken = jwtUtils.generateTokenFromUsername(user.getUsername());
          return new RefreshTokenResponse(newToken, token);
        })
        .orElseThrow(() ->  new RefreshTokenException(token, "Refresh token is invalid or expired"));
  }

}