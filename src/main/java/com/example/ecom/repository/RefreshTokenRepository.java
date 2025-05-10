package com.example.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.model.RefreshToken;
import com.example.ecom.model.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);
  Optional<RefreshToken> findByUser(User user);
  void deleteByUser(User user);
  
}