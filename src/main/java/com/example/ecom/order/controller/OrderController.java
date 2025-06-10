package com.example.ecom.order.controller;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.order.dtos.CreateOrderRequest;
import com.example.ecom.order.dtos.OrderDTO;
import com.example.ecom.order.dtos.OrderFilterDTO;
import com.example.ecom.order.dtos.UpdateOrderStatusRequest;
import com.example.ecom.order.service.OrderService;
import com.example.ecom.security.services.UserDetailsImpl;
import com.example.ecom.utils.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Operations related to orders")
@SecurityRequirement(name = "apiBearerAuth")

public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @Operation(summary = "Create a new order")
  public Result createOrder(
      @Parameter(description = "Order details", required = true) @Valid @RequestBody CreateOrderRequest request,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    UUID userId = userDetails.getId();
    OrderDTO order = orderService.createOrder(request, userId);
    return ResponseUtil.success("Order created successfully", order);
  }

  @GetMapping()
   @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "View a paginated list of orders with optional filtering and pagination (Admin Only)")
  public Result getAllOrders(@Valid @ModelAttribute @ParameterObject OrderFilterDTO orderFilterDto) {
    Page<OrderDTO> orders = orderService.getAllOrders(orderFilterDto);
    return ResponseUtil.success("Order list retrieved successfully", orders);
  }

  @GetMapping("/user/{userId}")
  @Operation(summary = "Get user orders")
  public Result getOrdersByUserId(
      @Parameter(description = "The userId used to retrieve the orders", required = true) @PathVariable UUID userId) {
    List<OrderDTO> orders = orderService.getUserOrders(userId);
    return ResponseUtil.success("Order list retrieved successfully", orders);
  }

  @GetMapping("/user/{userId}/count")
  @Operation(summary = "Count the number of user orders")
  public Result getUserOrderCount(
      @Parameter(description = "The userId used to retrieve the order count", required = true) @PathVariable UUID userId) {
    Long count = orderService.getUserOrderCount(userId);
    return ResponseUtil.success("User order count gotten successfully", count);
  }

  @PutMapping("/{id}/status")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Update the status of the order (Admin Only)")
  public Result updateOrderStatus(
      @Parameter(description = "ID of the order to update", required = true) @PathVariable UUID id,
      @Valid @RequestBody UpdateOrderStatusRequest request) {
    OrderDTO order = orderService.updateOrderStatus(id, request);
    return ResponseUtil.success("Order status updated successfully", order);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get order by ID")
  public Result getOrderById(
      @Parameter(description = "ID of the order to retrieve", required = true) @PathVariable UUID id) {
    OrderDTO order = orderService.getOrderById(id);
    return ResponseUtil.success("Order retrieved successfully", order);
  }

  @PutMapping("/{id}/cancel")
  @Operation(summary = "Cancel Order")
  public Result cancelOrder(
      @Parameter(description = "ID of the order to cancel", required = true) @PathVariable UUID id) {
    orderService.cancelOrder(id);
    return ResponseUtil.success("Order cancelled successfully", null);
  }

}
