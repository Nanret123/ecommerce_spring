package com.example.ecom.product.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.ecom.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductResponseDto toDto(Product product);

  Product toEntity(CreateProductDto createProductDto);

  void updateProductFromDto(UpdateProductDTO updateDto, @MappingTarget Product product);
}