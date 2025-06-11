package com.example.ecom.review.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating a new product review")
public class CreateReviewDto {
  @NotNull(message = "Product ID is required")
  @Schema(description = "ID of the product being reviewed", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID productId;

  @NotNull(message = "User ID is required")
  @Schema(description = "ID of the user submitting the review", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID userId;

  @NotNull(message = "Reviewer name is required")
  @Schema(description = "Name of the reviewer", example = "John Doe")
  private String reviewerName;

  @Size(max = 500, message = "Comment must be at most 500 characters long")
  private String comment;

  @NotNull(message = "Rating is required")
  @Schema(description = "Rating given by the reviewer, must be between 1 and 5", example = "4")
  private Integer rating;

}
