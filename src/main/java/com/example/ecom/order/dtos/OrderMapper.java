package com.example.ecom.order.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.ecom.model.Order;
import com.example.ecom.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(source = "userId", target = "userId")
  OrderDTO toDTO(Order order);

  Order toEntity(OrderDTO orderDTO);

  OrderItemDTO toDTO(OrderItemDTO orderItemDTO);

  OrderItem toEntity(OrderItemDTO orderItemDTO);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateOrderFromDTO(OrderDTO orderDTO, @MappingTarget Order order);

}
