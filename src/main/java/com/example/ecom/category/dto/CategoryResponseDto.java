package com.example.ecom.category.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for category response")
public class CategoryResponseDto {
  @Schema(description = "Unique identifier of the category", example = "1")
  private UUID id;

  @Schema(description = "Name of the category", example = "Electronics")
  private String name;

  @Schema(description = "Description of the category", example = "Electronic devices and gadgets")
  private String description;

  @Schema(description = "When the category was created", example = "2024-01-15T10:30:00")
  private String createdAt;

  @Schema(description = "When the category was last updated", example = "2024-01-16T14:45:00")
  private String updatedAt;
}
