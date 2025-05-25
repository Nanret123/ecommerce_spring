package com.example.ecom.repository;

import java.util.List;
import java.util.Optional; 
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.ecom.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
  // Find products by SKU
  Optional<Product> findBySku(String sku);

  List<Product> findByIsFeaturedTrue();

  Page<Product> findByInStockTrue(Pageable pageable);

}
