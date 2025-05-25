package com.example.ecom.product.service;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.example.ecom.model.Product;

public class ProductSpecification {
  public static Specification<Product> hasName(String name) {
    return (root, query, criteriaBuilder) -> {
      if (name == null || name.trim().isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    };
  }

  public static Specification<Product> hasCategoryId(UUID categoryId) {
    return (root, query, criteriaBuilder) -> {
      if (categoryId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.join("category").get("id"), categoryId);
    };
  }

  public static Specification<Product> hasCategoryName(String categoryName) {
    return (root, query, criteriaBuilder) -> {
      if (categoryName == null || categoryName.trim().isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(criteriaBuilder.lower(root.join("category").get("name")), "%" + categoryName.toLowerCase().trim() + "%");
    };
  }

  public static Specification<Product> isFeatured(Boolean isFeatured) {
    return (root, query, criteriaBuilder) -> {
      if (isFeatured == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("isFeatured"), isFeatured);
    };
  }

  public static Specification<Product> isInStock(Boolean inStock) {
    return (root, query, criteriaBuilder) -> {
      if (inStock == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("inStock"), inStock);
    };
  }

  public static Specification<Product> hasPriceRange(Double minPrice, Double maxPrice) {
    return (root, query, criteriaBuilder) -> {
      if (minPrice == null && maxPrice == null) {
        return criteriaBuilder.conjunction();
      }
      if (minPrice != null && maxPrice != null) {
        return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
      } else if (minPrice != null) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
      }
    };
  }
 

}