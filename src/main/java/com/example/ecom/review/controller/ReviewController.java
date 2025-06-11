package com.example.ecom.review.controller;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.review.dtos.CreateReviewDto;
import com.example.ecom.review.dtos.ReviewDto;
import com.example.ecom.review.dtos.UpdateReviewDto;
import com.example.ecom.review.service.ReviewService;
import com.example.ecom.security.services.UserDetailsImpl;
import com.example.ecom.utils.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Endpoints for product reviews")
public class ReviewController {
  private final ReviewService service;

  @PostMapping
  @Operation(summary = "Add a review to a product")
  public Result createReview(
      @Parameter(description = "Review details", required = true) @Valid @RequestBody CreateReviewDto reviewDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    UUID userId = userDetails.getId();
    ReviewDto createdReview = service.createReview(reviewDto, userId);
    return ResponseUtil.success("Review created successfully", createdReview);
  }

  @Operation(summary = "Get all reviews for a product")
  @GetMapping("/product/{productId}")
  public Result getProductReviews(
    @Parameter(description = "ID of the product to get reviews", required = true)
      @PathVariable UUID productId) {
    return ResponseUtil.success("Reviews gotten successfully", service.getReviewsByProductId(productId));
  }

  @Operation(summary = "Get one review")
  @GetMapping("/{reviewId}")
  public Result getOneReviews(
    @Parameter(description = "ID of the review to get", required = true)
      @PathVariable UUID reviewId) {
    return ResponseUtil.success("Review gotten successfully", service.getReviewById(reviewId));
  }

  @Operation(summary = "Update a review")
  @PutMapping("/{reviewId}")
  public Result updateReview(
      @Parameter(description = "ID of the review to update", required = true) @PathVariable UUID reviewId,
      @Parameter(description = "Updated review details", required = true) @Valid @RequestBody UpdateReviewDto updateReviewDto) {
    return ResponseUtil.success("Review updated successfully", service.updateReview(reviewId, updateReviewDto));
  }

  @Operation(summary = "Delete a review")
  @DeleteMapping("/{reviewId}")
  public Result deleteReview(
      @Parameter(description = "ID of the review to delete", required = true) @PathVariable UUID reviewId) {
     service.deleteReview(reviewId);
    return ResponseUtil.success("Review deleted successfully", null);
  }

}
