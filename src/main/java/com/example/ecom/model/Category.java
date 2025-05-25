package com.example.ecom.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@Schema(description = "Product category entity")
@ToString(exclude = "products")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier of the category", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @NotBlank(message = "Category name is required")
  @Column(unique = true, nullable = false)
  @Schema(description = "Name of the category", example = "Electronics", required = true)
  private String name;

  @Schema(description = "Description of the category", example = "Electronic devices and gadgets")
  private String description;

  @OneToMany(mappedBy = "category", cascade =  { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
  @Schema(description = "Products in this category")
  @JsonIgnore
  private List<Product> products = new ArrayList<>();

  @Schema(description = "When the category was created")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @Schema(description = "When the category was last updated")
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
    
    // Helper method to add product
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }
    
    // Helper method to remove product
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }

}
