package com.example.ecom.order.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.example.ecom.enums.OrderStatus;
import com.example.ecom.model.Order;

public class OrderSpecification {
  public static Specification<Order> hasUserId(UUID userId) {
    return (root, query, cb) -> {
      if (userId == null) {
        return cb.conjunction();
      }
      return cb.equal(root.get("userId"), userId);
    };
  }

  public static Specification<Order> hasStatus(OrderStatus status) {
    return (root, query, cb) -> {
      if (status == null) {
        return cb.conjunction();
      }
      return cb.equal(root.get("status"), status);
    };
  }

  public static Specification<Order> createdAfter(LocalDateTime startDate) {
    return (root, query, cb) -> {
      if (startDate == null) {
        return cb.conjunction();
      }
      return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);
    };
  }

  public static Specification<Order> createdBefore(LocalDateTime endDate) {
    return (root, query, cb) -> {
      if (endDate == null) {
        return cb.conjunction();
      }
      return cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
    };
  }

  public static Specification<Order> hasPaymentMethod(String paymentMethod) {
    return (root, query, cb) -> {
      if (paymentMethod == null || paymentMethod.trim().isEmpty())
        return cb.conjunction();
      return cb.like(cb.lower(root.get("paymentMethod")), "%" + paymentMethod.toLowerCase() + "%");
    };
  }

  public static Specification<Order> hasShippingAddress(String shippingAddress) {
    return (root, query, cb) -> {
      if (shippingAddress == null || shippingAddress.trim().isEmpty())
        return cb.conjunction();
      return cb.like(cb.lower(root.get("shippingAddress")), "%" + shippingAddress.toLowerCase() + "%");
    };
  }
}
