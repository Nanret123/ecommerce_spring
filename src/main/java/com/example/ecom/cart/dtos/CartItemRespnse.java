package com.example.ecom.cart.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response payload for a cart item")
public class CartItemRespnse {
  @Schema(description = "Unique identifier for the cart item", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Unique identifier for the product associated with the cart item", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID productId;

  @Schema(description = " the name of the product", example = "Bluetooth speaker")
  private String productName;

  @Schema(description = " the price of the product", example = "29.99")
  private BigDecimal price;

  @Schema(description = " the quantity of the product in the cart", example = "2")
  private Integer quantity;

  @Schema(description = " the total price for this item in the cart", example = "59.98")
  private BigDecimal subTotal;
}
