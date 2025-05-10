package com.example.ecom.model;

import java.time.Instant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(nullable = false, unique = true)
  @NotBlank
  private String token;

  @Column(nullable = false, name = "expiry_date")
  @NotBlank
  private Instant expiryDate;
  
  @Column(nullable = false, name = "created_date")
  @NotBlank
  private Instant createdDate;

  @PrePersist
  protected void setCreatedAt(){
    createdDate = Instant.now();
  }
}
