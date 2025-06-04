package com.example.ecom.order.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.ecom.enums.OrderStatus;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Order Filter Dto")
public class OrderFilterDTO {

  @Parameter(description = "Page number", example = "0")
  private int page = 0;

  @Parameter(description = "Number of items per page", example = "10")
  private int size = 10;

  @Parameter(description = "Sort by field", example = "id")
  private String sortBy = "id";

  @Parameter(description = "Sort direction (asc or desc)", example = "asc")
  private String sortDirection = "asc";

  @Parameter(description = "Filter orders by user ID", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID userId;

  @Parameter(description = "Filter orders by status (e.g., PENDING, COMPLETED, CANCELLED)", example = "PENDING")
  private OrderStatus status;

  @Parameter(description = "Start date for filtering orders (inclusive)", example = "2025-01-01T00:00:00")
  private LocalDateTime startDate;

  @Parameter(description = "End date for filtering orders (inclusive)", example = "2025-12-31T23:59:59")
  private LocalDateTime endDate;

  @Parameter(description = "Filter by payment method")
  private String paymentMethod;

  @Parameter(description = "Filter by shipping address")
  private String shippingAddress;
}
