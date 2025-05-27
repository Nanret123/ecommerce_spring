package com.example.ecom.cart.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for updating an item quantity in the cart")
public class UpdateCartItemRequest {
  @Schema(description = "Unique identifier for the cart item to be updated", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID productId;

  @Schema(description = "New quantity for the product in the cart", required = true, example = "2")
  private Integer quantity;
}
