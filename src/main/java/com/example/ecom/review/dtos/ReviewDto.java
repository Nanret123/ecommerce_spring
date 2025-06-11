package com.example.ecom.review.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
  private UUID id;
  private UUID productId;
  private UUID userId;
  private String reviewerName;
  private String comment;
  private Integer rating;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
