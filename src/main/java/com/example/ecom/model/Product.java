package com.example.ecom.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Product entity representing a product in the e-commerce system")
public class Product {

  @Schema(description = "Unique identifier of the product", example = "123e4567-e89b-12d3-a456-426614174000")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Schema(description = "then product name", example = "iPhone 13", required = true)
  @Column(nullable = false)
  @NotBlank(message = "Product name is required")
  private String name;

  @Schema(description = "Product description", example = "Latest Apple iPhone with A15 Bionic chip")
  private String description;

  @Schema(description = "Product image URL", example = "https://example.com/image.jpg")
  @NotBlank(message = "Image URL is required")
  @Column(name = "image_url")
  private String imageUrl;

  @Schema(description = "Product price", example = "999.99")
  @NotNull(message = "Price is required")
  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  @Schema(description = "Product quantity in stock", example = "100")
  @NotNull(message = "Quantity in stock is required")
  private Integer quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = true)
  @Schema(description = "Category of the product")
  private Category category;

  @Schema(description = "Whether the product is featured")
  @Builder.Default
  private Boolean isFeatured = false;

  @Schema(description = "Whether the product is in stock")
  @Builder.Default
  private Boolean inStock = true;

  @Schema(description = "Product SKU")
  @Column(unique = true)
  private String sku;

  @Column(name = "created_at")
  @Schema(description = "Creation timestamp of the product")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @Schema(description = "Last update timestamp of the product")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
