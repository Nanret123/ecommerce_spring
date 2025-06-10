package com.example.ecom.order.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request payload for creating an order")
public class CreateOrderRequest {
  @NotBlank(message = "Shipping address is required")
  @Schema(description = "Shipping address for the order", example = "123 Main St, Springfield", required = true)
  private String shippingAddress;

  @NotBlank(message = "Payment method is required")
  @Schema(description = "Payment method chosen by the user", example = "Credit Card", required = true)
  private String paymentMethod;

  @NotNull(message = "Cart ID is required")
  @Schema(description = "ID of the user's cart", example = "a2b9c10e-36dd-4973-b567-8d34c2e9c401", required = true)
  private UUID cartId;
}
