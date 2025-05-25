package com.example.ecom.product.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.ecom.model.Product;
import com.example.ecom.product.dto.CreateProductDto;
import com.example.ecom.product.dto.ProductFilterDto;
import com.example.ecom.product.dto.ProductResponseDto;
import com.example.ecom.product.dto.UpdateProductDTO;

public interface IProduct {
  Page<ProductResponseDto> getAllProducts(ProductFilterDto filterDto);

  Optional<ProductResponseDto> getProductById(UUID id);

  ProductResponseDto createProduct(CreateProductDto product);

  ProductResponseDto updateProduct(UUID id, UpdateProductDTO product);

  void deleteProduct(UUID id);

  Product getProductEntityById(UUID id);

  ProductResponseDto markAsFeatured(UUID id, boolean featured);

  ProductResponseDto toggleInStock(UUID id);
}