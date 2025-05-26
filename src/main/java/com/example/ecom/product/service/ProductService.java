package com.example.ecom.product.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.model.Category;
import com.example.ecom.model.Product;
import com.example.ecom.product.dto.CreateProductDto;
import com.example.ecom.product.dto.ProductFilterDto;
import com.example.ecom.product.dto.ProductMapper;
import com.example.ecom.product.dto.ProductResponseDto;
import com.example.ecom.product.dto.UpdateProductDTO;
import com.example.ecom.product.interfaces.IProduct;
import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProduct {

  private final ProductRepository productRepo;
  private final CategoryRepository categoryRepo;
  private final ProductMapper productMapper;

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponseDto> getAllProducts(ProductFilterDto filterDto) {

    // build the specification based on the filter criteria
    Specification<Product> spec = buildSpecification(filterDto);

    // create pageable object for pagination and sorting
    Pageable pageable = createPageable(filterDto);

    // fetch products from the repository with pagination and filtering
    Page<Product> productPage = productRepo.findAll(spec, pageable);

    // convert the product entities to DTOs
    return productPage.map(this::convertToResponseDTO);

  }

  @Override
  public Optional<ProductResponseDto> getProductById(UUID id) {
    Product product = productRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product Not Found"));
    return Optional.of(convertToResponseDTO(product));
  }

  @Override
  public ProductResponseDto createProduct(CreateProductDto product) {

     Product productEntity = productMapper.toEntity(product);
    // check if categoryid exists
    if (product.getCategoryId() != null) {
      Category category = categoryRepo.findById(product.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Category Not Found"));
      productEntity.setCategory(category);
    }
   
    Product savedProduct = productRepo.save(productEntity);
    return productMapper.toDto(savedProduct);
  }

  @Override
  public ProductResponseDto updateProduct(UUID id, UpdateProductDTO updateProductDto) {
    Product existingProduct = getProductEntityById(id);

    productMapper.updateProductFromDto(updateProductDto, existingProduct);

    Product updatedProduct = productRepo.save(existingProduct);

    return productMapper.toDto(updatedProduct);

  }

  @Override
  public ProductResponseDto markAsFeatured(UUID id, boolean featured) {
    Product product = getProductEntityById(id);
    product.setIsFeatured(featured);
    Product new_prod =  productRepo.save(product);
         return productMapper.toDto(new_prod);
  }

  @Override
  public ProductResponseDto toggleInStock(UUID id) {
    Product product = getProductEntityById(id);
    product.setInStock(!product.getInStock());
    Product updatedProduct = productRepo.save(product);
    return productMapper.toDto(updatedProduct);
  }

  @Override
  public void deleteProduct(UUID id) {
    Product productToDelete = getProductEntityById(id);
    productRepo.delete(productToDelete);
  }

  @Override
  public ProductResponseDto updateStockQuantity(UUID id, Integer quantity) {
    Product product = getProductEntityById(id);
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    product.setQuantity(quantity);
    Product updatedProduct = productRepo.save(product);
    return productMapper.toDto(updatedProduct);
  }

  @Override
  public ProductResponseDto addQuantity(UUID id, Integer quantity) {
    Product product = getProductEntityById(id);
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    product.setQuantity(product.getQuantity() + quantity);
    Product updatedProduct = productRepo.save(product);
    return productMapper.toDto(updatedProduct);
  }

  @Override
  public ProductResponseDto reduceQuantity(UUID id, Integer quantity) {
    Product product = getProductEntityById(id);
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    if (product.getQuantity() < quantity) {
      throw new IllegalArgumentException("Insufficient stock to reduce by " + quantity);
    }
    product.setQuantity(product.getQuantity() - quantity);
    Product updatedProduct = productRepo.save(product);
    return productMapper.toDto(updatedProduct);
  }

  @Override
  public Product getProductEntityById(UUID id) {
    return productRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product Not Found"));
  }

  private ProductResponseDto convertToResponseDTO(Product product) {
    return new ProductResponseDto(
        product.getId(),
        product.getName(),
        product.getCategory().getName(),
        product.getPrice(),
        product.getDescription(),
        product.getImageUrl(),
        product.getQuantity(),
        product.getIsFeatured(),
        product.getInStock(),
        product.getSku(),
        product.getCreatedAt(),
        product.getUpdatedAt());
  }

  private Specification<Product> buildSpecification(ProductFilterDto filterDto) {
    Specification<Product> spec = Specification.where(null);

    if (filterDto.getNameFilter() != null && !filterDto.getNameFilter().trim().isEmpty()) {
      spec = spec.and(ProductSpecification.hasName(filterDto.getNameFilter()));
    }

    if (filterDto.getCategoryId() != null) {
      spec = spec.and(ProductSpecification.hasCategoryId(filterDto.getCategoryId()));
    }

    if (filterDto.getCategoryName() != null && !filterDto.getCategoryName().trim().isEmpty()) {
      spec = spec.and(ProductSpecification.hasCategoryName(filterDto.getCategoryName()));
    }

    if (filterDto.getMinPrice() != null || filterDto.getMaxPrice() != null) {
      Double minPrice = filterDto.getMinPrice() != null ? filterDto.getMinPrice().doubleValue() : null;
      Double maxPrice = filterDto.getMaxPrice() != null ? filterDto.getMaxPrice().doubleValue() : null;
      spec = spec.and(ProductSpecification.hasPriceRange(minPrice, maxPrice));
    }

    if (filterDto.getInStock() != null) {
      spec = spec.and(ProductSpecification.isInStock(filterDto.getInStock()));
    }

    if (filterDto.getIsFeatured() != null) {
      spec = spec.and(ProductSpecification.isFeatured(filterDto.getIsFeatured()));
    }

    return spec;
  }

  private Pageable createPageable(ProductFilterDto filterDto) {
    Sort.Direction direction = "desc".equalsIgnoreCase(filterDto.getSortDirection())
        ? Sort.Direction.DESC
        : Sort.Direction.ASC;

    Sort sort = Sort.by(direction, filterDto.getSortBy());

    return PageRequest.of(filterDto.getPage(), filterDto.getSize(), sort);
  }

}
