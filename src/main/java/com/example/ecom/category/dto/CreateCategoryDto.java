package com.example.ecom.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for creating or updating a category")
public class CreateCategoryDto {
  @NotBlank(message = "Category name is required")
  @Schema(description = "Name of the category", example = "Electronics", required = true)
  private String name;

  @Schema(description = "Description of the category", example = "Electronic devices and gadgets")
  private String description;

}
