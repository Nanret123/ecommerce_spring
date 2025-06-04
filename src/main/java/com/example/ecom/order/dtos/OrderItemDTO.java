package com.example.ecom.order.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
  private UUID id;
  private UUID productId;
  private String productName;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
}
