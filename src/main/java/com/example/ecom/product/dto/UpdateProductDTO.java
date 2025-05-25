package com.example.ecom.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for updating an existing Product")
public class UpdateProductDTO {

    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Schema(description = "Name of the Product", example = "Premium Headphones", required = true)
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Schema(description = "Description of the Product", example = "High-quality noise-cancelling headphones")
    private String description;

    @Min(value = 0, message = "Price must be non-negative")
    @Schema(description = "Price of the Product", example = "199.99", required = true)
    private BigDecimal price;

     @Schema(description = "Category ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID categoryId;
    
    @Schema(description = "Whether the product is featured", example = "false")
    private Boolean isFeatured;
    
    @Schema(description = "Whether the product is in stock", example = "true")
    private Boolean inStock;
    
    @Schema(description = "Product SKU", example = "IPH-13-128-BLK")
    private String sku;
    
    @Schema(description = "Main image URL of the Product", example = "https://example.com/images/headphones.jpg")
    private String imageUrl;

}