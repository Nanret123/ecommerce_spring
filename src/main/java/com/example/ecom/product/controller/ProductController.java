package com.example.ecom.product.controller;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.product.dto.CreateProductDto;
import com.example.ecom.product.dto.ProductFilterDto;
import com.example.ecom.product.dto.ProductResponseDto;
import com.example.ecom.product.dto.UpdateProductDTO;
import com.example.ecom.product.service.ProductService;
import com.example.ecom.utils.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management APIs")
@SecurityRequirement(name = "apiBearerAuth")
public class ProductController {

  private final ProductService productService;

  @GetMapping()
  @Operation(summary = "View a paginated list of available products with optional filtering and pagination")
  public Result getAllProducts(@Valid @ModelAttribute @ParameterObject ProductFilterDto productFilterDto) {

    Page<ProductResponseDto> products = productService.getAllProducts(productFilterDto);
    return ResponseUtil.success("Product list retrieved successfully", products);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get product by ID")
  public Result getProductById(
      @Parameter(description = "ID of the product to retrieve", required = true) @PathVariable UUID id) {
    return productService.getProductById(id)
        .map(product -> ResponseUtil.success("Product retrieved successfully", product))
        .orElse(ResponseUtil.error("Product not found"));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Create a new product (Admins Only)")
  public Result createProduct(
      @Parameter(description = "Product details", required = true) @Valid @RequestBody CreateProductDto productDto) {
    ProductResponseDto createdProduct = productService.createProduct(productDto);
    return ResponseUtil.success("Product created successfully", createdProduct);
  }

  @PatchMapping("/{id}/featured")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Mark a product as featured (Admins Only)")
  public Result markAsFeatured(
      @Parameter(description = "ID of the product to mark as featured", required = true) @PathVariable UUID id,
      @Parameter(description = "Whether to mark as featured or not", required = true) @RequestParam boolean featured) {
    ProductResponseDto updatedProduct = productService.markAsFeatured(id, featured);
    return ResponseUtil.success("Product marked as featured successfully", updatedProduct);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Update an existing product (Admins Only)")
  public Result updateProduct(
      @Parameter(description = "ID of the product to update", required = true) @PathVariable UUID id,
      @Parameter(description = "Updated product details", required = true) @RequestBody @Valid UpdateProductDTO productDto) {
    ProductResponseDto updatedProduct = productService.updateProduct(id, productDto);
    return ResponseUtil.success("Product updated successfully", updatedProduct);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Delete a product by ID (Admins Only)")
  public Result deleteProduct(
      @Parameter(description = "ID of the product to delete", required = true) @PathVariable UUID id) {
    productService.deleteProduct(id);
    return ResponseUtil.success("Product deleted successfully", null);
  }




}
