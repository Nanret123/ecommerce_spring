package com.example.ecom.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ecom.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
  List<Review> findByProductId(UUID productId);

  @Query("SELECT COUNT(r) FROM Review r WHERE r.productId = :productId")
    Long countReviewsByProductId(@Param("productId") UUID productId);

  @Query("SELECT AVG(r.rating) FROM Review r WHERE r.productId = :productId")
    Optional<Double> findAverageRatingByProductId(@Param("productId") UUID productId);

    boolean existsByProductIdAndUserId(UUID productId, UUID userId);
}
