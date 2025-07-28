package com.example.ecom.category.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.category.dto.CategoryRequestDTO;
import com.example.ecom.category.dto.CategoryResponseDto;
import com.example.ecom.category.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/categories")
@Validated
@RequiredArgsConstructor
@Tag(name = "Category", description = "Operations related to categories")
@SecurityRequirement(name = "apiBearerAuth")
public class CategoryController {
  
  private final CategoryService service;

  @GetMapping()
  @Operation(summary = "Get all categories")
  public Result< List<CategoryResponseDto>> getAllCategories() {
    List<CategoryResponseDto>categories =  service.getAllCategories();
    return Result.success("Categories retrieved successfully", categories);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get category by ID")
  public Result<CategoryResponseDto> getCategoryById(@PathVariable UUID id) {
    CategoryResponseDto category = service.getCategoryById(id);
    return Result.success("Category retrieved successfully", category);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Create a new category (Admins Only)")
  public Result<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDTO categoryDto) {
    CategoryResponseDto createdCategory = service.createCategory(categoryDto);
    return Result.success("Category created successfully", createdCategory);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Update an existing category (Admins Only)")
  public Result<CategoryResponseDto> updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequestDTO categoryDto) {
    CategoryResponseDto updatedCategory = service.updateCategory(id, categoryDto);
    return Result.success("Category updated successfully", updatedCategory);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Delete a category by ID (Admins Only)")
  public Result<Void> deleteCategory(@PathVariable UUID id) {
    service.deleteCategory(id);
    return Result.success("Category deleted successfully", null);
  }
}
