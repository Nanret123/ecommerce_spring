package com.example.ecom.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(nullable = false, unique = true)
  @NotBlank
  private String token;

  @Column(nullable = false, name = "expiry_date")
  @NotNull
  private Instant expiryDate;
  
  @Column(nullable = false, name = "created_date")
  @NotNull
  private Instant createdDate;

  @PrePersist
  protected void setCreatedAt(){
    createdDate = Instant.now();
  }
}
