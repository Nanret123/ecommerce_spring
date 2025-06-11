package com.example.ecom.review.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.model.Review;
import com.example.ecom.repository.ReviewRepository;
import com.example.ecom.review.dtos.CreateReviewDto;
import com.example.ecom.review.dtos.ReviewDto;
import com.example.ecom.review.dtos.ReviewMapper;
import com.example.ecom.review.dtos.UpdateReviewDto;
import com.example.ecom.review.interfaces.IReview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService implements IReview {
  private final ReviewRepository reviewRepo;
  private final ReviewMapper reviewMapper;

  @Override
  public ReviewDto createReview(CreateReviewDto createReviewDto) {
    // check if user has reviewed the product
    if (hasUserReviewedProduct(createReviewDto.getProductId(), createReviewDto.getUserId())) {
      throw new IllegalArgumentException("User has already reviewed this product");
    }
    Review review = reviewMapper.toEntity(createReviewDto);

    Review savedReview = reviewRepo.save(review);
    return reviewMapper.toDto(savedReview);

  }

  @Override
  @Transactional(readOnly = true)
  public ReviewDto getReviewById(UUID reviewId) {
    Review review = reviewRepo.findById(reviewId)
        .orElseThrow(() -> new RuntimeException("Review Not Found"));
    return reviewMapper.toDto(review);

  }

  @Override
  public List<ReviewDto> getReviewsByProductId(UUID productId) {
    return reviewRepo.findByProductId(productId).stream()
        .map(reviewMapper::toDto)
        .toList();
  }

  @Override
  public ReviewDto updateReview(UUID reviewId, UpdateReviewDto updateReviewDto) {
    Review review = getReviewEntityById(reviewId);
    reviewMapper.updateEntityFromDto(updateReviewDto, review);
    Review updatedReview = reviewRepo.save(review);
    return reviewMapper.toDto(updatedReview);
  }

  @Override
  public void deleteReview(UUID id) {
    getReviewById(id);
    reviewRepo.deleteById(id);
  }

  @Override
  public Review getReviewEntityById(UUID id) {
    return reviewRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Review Not Found"));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean hasUserReviewedProduct(UUID productId, UUID userId) {
    // Check if the user has already reviewed the product
    return reviewRepo.existsByProductIdAndUserId(productId, userId);

  }
}
