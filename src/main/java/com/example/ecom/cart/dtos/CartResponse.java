package com.example.ecom.cart.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description="Response payload for a shopping cart")
public class CartResponse {
  @Schema(description = "Unique identifier for the cart", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Unique identifier for the user who owns the cart", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID userId;

@Schema(description = "List of items in the cart")
  private List<CartItemRespnse> items;

  @Schema(description = "Total number of items in the cart", example = "3")
  private Integer totalItems;

  @Schema(description = "Total amount for all items in the cart", example = "99.99")
  private BigDecimal totalAmount;
}
