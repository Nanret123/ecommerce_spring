package com.example.ecom.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecom.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  // Find category by name (case-insensitive)
  Optional<Category> findByNameIgnoreCase(String name);

  // Check if category exists by name (for validation)
  boolean existsByNameIgnoreCase(String name);


  // Find all categories with pagination, ordered by name
  Page<Category> findAllByOrderByNameAsc(Pageable pageable);

  // Find all categories ordered by name
  List<Category> findAllByOrderByNameAsc();


  // Custom query to get categories with product count
  @Query("SELECT c FROM Category c LEFT JOIN Product p ON p.category = c GROUP BY c ORDER BY c.name ASC")
List<Category> findCategoriesWithProductCount();
}
