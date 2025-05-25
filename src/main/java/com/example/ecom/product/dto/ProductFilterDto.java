package com.example.ecom.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class ProductFilterDto {
  @Parameter(description = "Page number", example = "0")
  private int page = 0;

  @Parameter(description = "Number of items per page", example = "10")
  private int size = 10;

  @Parameter(description = "Sort by field", example = "id")
  private String sortBy = "id";

  @Parameter(description = "Sort direction (asc or desc)", example = "asc")
  private String sortDirection = "asc";

  @Parameter(description = "Filter by product name (optional)")
  private String nameFilter;

   @Parameter(description = "Filter by category ID (optional)")
  private UUID categoryId;

  @Parameter(description = "Filter by category name (optional)")
  private String categoryName;

 @Parameter(description = "Minimum price (optional)")
  private BigDecimal minPrice;

  @Parameter(description = "Maximum price (optional)")
  private BigDecimal maxPrice;

  @Parameter(description = "Filter by in stock (true or false)")
  private Boolean inStock;

   @Parameter(description = "Filter by featured(true or false)")
  private Boolean isFeatured;
}
