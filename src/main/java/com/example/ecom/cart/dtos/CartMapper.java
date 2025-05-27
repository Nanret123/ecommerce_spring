package com.example.ecom.cart.dtos;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.ecom.model.Cart;
import com.example.ecom.model.CartItem;

@Mapper(componentModel = "spring")
public interface  CartMapper {

  @Mapping(target = "items", source = "cartItems")
  @Mapping(target = "totalItems", expression = "java(calculateTotalItems(cart.getCartItems()))")
  @Mapping(target = "totalAmount", expression = "java(calculateTotalAmount(cart.getCartItems()))")
  CartResponse toCartResponse(Cart cart);
  
  @Mapping(target = "productId", source = "product.id")
  @Mapping(target = "productName", source = "product.name")
  @Mapping(target = "subTotal", expression =  "java(cartItem.getSubTotal())")
  CartItemRespnse toCartItemResponse(CartItem cartItem);

  List<CartItemRespnse> toCartItemResponseList(List<CartItem> cartItems);

  default Integer calculateTotalItems(List<CartItem> cartItems) {
    if(cartItems == null || cartItems.isEmpty()) {
      return 0;
    }
    return cartItems.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();
  }
  default BigDecimal calculateTotalAmount(List<CartItem> cartItems) {
    if(cartItems == null || cartItems.isEmpty()) {
      return BigDecimal.ZERO;
    }
    return cartItems.stream()
                    .map(CartItem::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
