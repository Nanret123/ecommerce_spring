package com.example.ecom.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "CreateProductDto", description = "Data Transfer Object for creating a new product")
public class CreateProductDto {
    @Schema(description = "Name of the Product", example = "Premium Headphones", required = true)
    @NotBlank(message = "Product name is required")
    private String name;

   @Schema(description = "Category ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID categoryId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Schema(description = "Price of the Product", example = "199.99", required = true)
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    @Schema(description = "Description of the Product", example = "High-quality noise-cancelling headphones", required = true)
    private String description;

    @NotNull(message = "Quantity is required")
    @Schema(description = "Available quantity of the Product", example = "50", required = true)
    private Integer quantity;

    @NotBlank(message = "Image URL is required")
    @Schema(description = "Main image URL of the Product", example = "https://example.com/images/headphones.jpg", required = true)
    private String imageUrl;

     @Schema(description = "Whether the product is featured", example = "false")
    private Boolean isFeatured = false;
    
    @Schema(description = "Whether the product is in stock", example = "true")
    private Boolean inStock = true;
    
    @Schema(description = "Product SKU", example = "IPH-13-128-BLK")
    private String sku;
}
