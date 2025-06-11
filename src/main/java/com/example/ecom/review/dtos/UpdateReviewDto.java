package com.example.ecom.review.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for updating a product review")
public class UpdateReviewDto {
  @Schema(description = "ID of the review to be updated", example = "123e4567-e89b-12d3-a456-426614174000")
  private Integer rating;

  @Schema(description = "Updated comment for the review", example = "This product is great!")
  private String comment;
}
