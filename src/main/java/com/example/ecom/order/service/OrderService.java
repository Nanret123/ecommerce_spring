package com.example.ecom.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.cart.service.CartService;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.model.Cart;
import com.example.ecom.model.Order;
import com.example.ecom.model.OrderItem;
import com.example.ecom.order.dtos.CreateOrderRequest;
import com.example.ecom.order.dtos.OrderDTO;
import com.example.ecom.order.dtos.OrderFilterDTO;
import com.example.ecom.order.dtos.OrderMapper;
import com.example.ecom.order.dtos.UpdateOrderStatusRequest;
import com.example.ecom.repository.CartRepository;
import com.example.ecom.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
  private final OrderRepository orderRepo;
  private final OrderMapper orderMapper;
  private final CartRepository cartRepo;
  private final CartService cartService;

  public OrderDTO createOrder(CreateOrderRequest request) {
    // get cart and its items
    Cart cart = cartRepo.findByUserIdWithItems(request.getUserId())
        .orElse(null);

    if (cart.getCartItems().isEmpty()) {
      throw new IllegalStateException("cannot create order from an empty cart");
    }

    // calculate total amount
    BigDecimal totalAmount = cart.getCartItems().stream()
        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Create order
    Order order = new Order();
    order.setOrderNumber(generateOrderNumber());
    order.setUserId(request.getUserId());
    order.setTotalAmount(totalAmount);
    order.setStatus(OrderStatus.PENDING);
    order.setShippingAddress(request.getShippingAddress());
    order.setPaymentMethod(request.getPaymentMethod());

    List<OrderItem> orderItems = cart.getCartItems().stream()
        .map(cartItem -> {
          OrderItem item = new OrderItem();
          item.setOrder(order);
          item.setProductId(cartItem.getProduct().getId());
          item.setProductName(cartItem.getProduct().getName());
          item.setQuantity(cartItem.getQuantity());
          item.setUnitPrice(cartItem.getPrice());
          item.setTotalPrice(cartItem.getSubTotal());
          return item;
        })
        .collect(Collectors.toList());

    order.setOrderItems(orderItems);

    Order savedOrder = orderRepo.save(order);

    cartService.clearCart(request.getUserId());

    return orderMapper.toDTO(savedOrder);
  }

  @Transactional(readOnly = true)
  public OrderDTO getOrderById(UUID id) {
    Order order = orderRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    return orderMapper.toDTO(order);
  }

  @Transactional(readOnly = true)
  public List<OrderDTO> getUserOrders(UUID userId) {
    List<Order> orders = orderRepo.findByUserIdOrderByCreatedAtDesc(userId);
    return orders.stream()
        .map(orderMapper::toDTO)
        .collect(Collectors.toList());
  }

  public Long getUserOrderCount(UUID userId) {
    return orderRepo.countOrdersByUserId(userId);
  }

  public Page<OrderDTO> getAllOrders(OrderFilterDTO filter) {
    Specification<Order> spec = buildSpecification(filter);

     Pageable pageable = createPageable(filter);

    Page<Order> orders = orderRepo.findAll(spec, pageable);
    return orders.map(orderMapper::toDTO);
  }

  public OrderDTO updateOrderStatus(UUID orderId, UpdateOrderStatusRequest request) {
    Order order = getOrderEntityById(orderId);
    order.setStatus(request.getStatus());

    Order updatedOrder = orderRepo.save(order);

    return orderMapper.toDTO(updatedOrder);

  }

  public void cancelOrder(UUID orderId){
    Order order = getOrderEntityById(orderId);

    if(order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
      throw new IllegalStateException("Cannot cancel order with status: " + order.getStatus());
    }

    order.setStatus(OrderStatus.CANCELLED);
    orderRepo.save(order);
  }

  public Order getOrderEntityById(UUID id) {
    return orderRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Order Not Found"));
  }

  private Specification<Order> buildSpecification(OrderFilterDTO filter) {
    return Specification
        .where(OrderSpecification.hasUserId(filter.getUserId()))
        .and(OrderSpecification.hasStatus(filter.getStatus()))
        .and(OrderSpecification.createdAfter(filter.getStartDate()))
        .and(OrderSpecification.createdBefore(filter.getEndDate()))
        .and(OrderSpecification.hasPaymentMethod(filter.getPaymentMethod()))
        .and(OrderSpecification.hasShippingAddress(filter.getShippingAddress()));
  }

  private String generateOrderNumber() {
    String prefix = "ORD";
    String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    String suffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    return prefix + "-" + timeStamp + "-" + suffix;
  }

   private Pageable createPageable(OrderFilterDTO filterDto) {
    Sort.Direction direction = "desc".equalsIgnoreCase(filterDto.getSortDirection())
        ? Sort.Direction.DESC
        : Sort.Direction.ASC;

    Sort sort = Sort.by(direction, filterDto.getSortBy());

    return PageRequest.of(filterDto.getPage(), filterDto.getSize(), sort);
  }
}
