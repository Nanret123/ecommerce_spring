package com.example.ecom.cart.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for adding a product to the cart")
public class AddToCartRequest {
  @Schema(description="ID of the user adding the product to the cart", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID productId;
  @Schema(description="Quantity of the product to be added to the cart", required = true, example = "1")
  private Integer quantity;
}
