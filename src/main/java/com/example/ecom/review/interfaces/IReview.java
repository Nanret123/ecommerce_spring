package com.example.ecom.review.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.ecom.model.Review;
import com.example.ecom.review.dtos.CreateReviewDto;
import com.example.ecom.review.dtos.ReviewDto;
import com.example.ecom.review.dtos.UpdateReviewDto;

public interface IReview {
  ReviewDto createReview(CreateReviewDto createReviewDto, UUID userId);

  ReviewDto getReviewById(UUID reviewId);

  List<ReviewDto> getReviewsByProductId(UUID productId);

  ReviewDto updateReview(UUID reviewId, UpdateReviewDto updateReviewDto);

  void deleteReview(UUID reviewId);

  boolean hasUserReviewedProduct(UUID productId, UUID userId);

  Review getReviewEntityById(UUID id); 
}
