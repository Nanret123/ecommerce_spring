package com.example.ecom.order.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
  private UUID userId;
  private String shippingAddress;
  private String paymentMethod;
  private UUID cartId;
}
