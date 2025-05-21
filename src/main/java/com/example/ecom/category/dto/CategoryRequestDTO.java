package com.example.ecom.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating or updating a category")
public class CategoryRequestDTO {
    
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    @Schema(description = "Name of the category", example = "Electronics", required = true)
    private String name;
    
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @Schema(description = "Description of the category", example = "Electronic devices and gadgets")
    private String description;
}