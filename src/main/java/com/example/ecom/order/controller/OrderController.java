package com.example.ecom.order.controller;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.order.dtos.CreateOrderRequest;
import com.example.ecom.order.dtos.OrderDTO;
import com.example.ecom.order.dtos.OrderFilterDTO;
import com.example.ecom.order.dtos.UpdateOrderStatusRequest;
import com.example.ecom.order.service.OrderService;
import com.example.ecom.utils.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public Result createOrder(@Valid @RequestBody CreateOrderRequest request) {
    OrderDTO order = orderService.createOrder(request);
    return ResponseUtil.success("Order created successfully", order);
  }

  @GetMapping()
  @Operation(summary = "View a paginated list of user orders with optional filtering and pagination")
  public Result getAllOrders(@Valid @ModelAttribute @ParameterObject OrderFilterDTO orderFilterDto) {
    Page<OrderDTO> orders = orderService.getAllOrders(orderFilterDto);
    return ResponseUtil.success("Order list retrieved successfully", orders);
  }

  @GetMapping("/user/{userId}")
  public Result getOrdersByUserId(@PathVariable UUID userId) {
    List<OrderDTO> orders = orderService.getUserOrders(userId);
    return ResponseUtil.success("Order list retrieved successfully", orders);
  }

  @GetMapping("/user/{userId}/count")
  public Result getUserOrderCount(@PathVariable UUID userId) {
    Long count = orderService.getUserOrderCount(userId);
    return ResponseUtil.success("User order count gotten successfully", count);
  }

  @PutMapping("/{id}/status")
  public Result updateOrderStatus(
      @PathVariable UUID id,
      @Valid @RequestBody UpdateOrderStatusRequest request) {
    OrderDTO order = orderService.updateOrderStatus(id, request);
    return ResponseUtil.success("User status updated successfully", order);
  }

  @GetMapping("/{id}")
  public Result getOrderById(
      @Parameter(description = "ID of the  to retrieve", required = true) @PathVariable UUID id) {
    OrderDTO order = orderService.getOrderById(id);
    return ResponseUtil.success("Order retrieved successfully", order);
  }

  @PutMapping("/{id}/cancel")
  public Result cancelOrder(@PathVariable UUID id) {
    orderService.cancelOrder(id);
    return ResponseUtil.success("Order cancelled successfully", null);
  }

}
