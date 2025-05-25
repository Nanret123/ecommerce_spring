package com.example.ecom.category.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.category.dto.CategoryRequestDTO;
import com.example.ecom.category.dto.CategoryResponseDto;
import com.example.ecom.model.Category;
import com.example.ecom.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepo;
  private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



  /**
   * Get all active categories without pagination (for dropdowns, etc.)
   */

  public List<CategoryResponseDto> getAllCategories() {
    List<Category> categories = categoryRepo.findAllByOrderByNameAsc();
    return categories.stream()
        .map(this::convertToResponseDTO)
        .collect(Collectors.toList());
  }

  /**
   * Get category by ID
   */
  public CategoryResponseDto getCategoryById(UUID id) {
    Category category = categoryRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    return convertToResponseDTO(category);
  }

  /**
   * Create new category
   */
  public CategoryResponseDto createCategory(CategoryRequestDTO requestDTO) {
    // Check if category name already exists
    if (categoryRepo.existsByNameIgnoreCase(requestDTO.getName())) {
      throw new RuntimeException("Category with name '" + requestDTO.getName() + "' already exists");
    }
    Category category = new Category();
    category.setName(requestDTO.getName());
    category.setDescription(requestDTO.getDescription());

    Category savedCategory = categoryRepo.save(category);
    return convertToResponseDTO(savedCategory);
  }

  /**
   * Update existing category
   */
  public CategoryResponseDto updateCategory(UUID id, CategoryRequestDTO requestDTO) {
    Category existingCategory = categoryRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

    // Check if the new name conflicts with existing categories (excluding current
    // one)
    if (!existingCategory.getName().equalsIgnoreCase(requestDTO.getName()) &&
        categoryRepo.existsByNameIgnoreCase(requestDTO.getName())) {
      throw new RuntimeException("Category with name '" + requestDTO.getName() + "' already exists");
    }

    existingCategory.setName(requestDTO.getName());
    existingCategory.setDescription(requestDTO.getDescription());

    Category updatedCategory = categoryRepo.save(existingCategory);
    return convertToResponseDTO(updatedCategory);
  }

  /**
   * Delete category by ID
   */
  public void deleteCategory(UUID id) {
    Category category = categoryRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    categoryRepo.delete(category);
  }

  /**
   * Convert Category entity to CategoryResponseDTO
   */
  private CategoryResponseDto convertToResponseDTO(Category category) {
    CategoryResponseDto dto = new CategoryResponseDto();
    dto.setId(category.getId());
    dto.setName(category.getName());
    dto.setDescription(category.getDescription());
    dto.setCreatedAt(category.getCreatedAt() != null ? category.getCreatedAt().format(dateFormatter) : null);
    dto.setUpdatedAt(category.getUpdatedAt() != null ? category.getUpdatedAt().format(dateFormatter) : null);
    return dto;
  }

}
