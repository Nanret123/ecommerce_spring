package com.example.ecom.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.ecom.model.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductDto", description = "Data Transfer Object for Product")
public class ProductResponseDto {
    @Schema(description = "Unique identifier of the Product", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Name of the Product", example = "Premium Headphones")
    private String name;

     @Schema(description = "Category information")
    private Category category;

    @Schema(description = "Price of the Product", example = "199.99")
    private BigDecimal price;

    @Schema(description = "Description of the Product", example = "High-quality noise-cancelling headphones")
    private String description;

    @Schema(description = "Main image URL of the Product", example = "https://example.com/images/headphones.jpg")
    private String imageUrl;

     @Schema(description = "Whether the product is featured", example = "false")
    private Boolean isFeatured;
    
    @Schema(description = "Whether the product is in stock", example = "true")
    private Boolean inStock;
    
    @Schema(description = "Product SKU", example = "IPH-13-128-BLK")
    private String sku;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    
}
