package com.example.ecom.order.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.ecom.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private UUID id;
  private String orderNumber;
  private UUID userId;
  private BigDecimal totalAmount;
  private OrderStatus status;
  private String shippingAddress;
  private String paymentMethod;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private List<OrderItemDTO> orderItems;
}
