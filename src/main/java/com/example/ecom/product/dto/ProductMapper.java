package com.example.ecom.product.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.ecom.model.Product;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
  ProductResponseDto toDto(Product product);

  @Mapping(target = "category", ignore = true) 
  Product toEntity(CreateProductDto createProductDto);

  void updateProductFromDto(UpdateProductDTO updateDto, @MappingTarget Product product);
}