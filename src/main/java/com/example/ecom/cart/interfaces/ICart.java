package com.example.ecom.cart.interfaces;

import java.util.UUID;

import com.example.ecom.cart.dtos.AddToCartRequest;
import com.example.ecom.cart.dtos.CartResponse;
import com.example.ecom.cart.dtos.UpdateCartItemRequest;

public interface ICart {
  CartResponse addToCart(UUID userId, AddToCartRequest request);

  CartResponse updateCartItem(UpdateCartItemRequest request);

  void removeCartItem(UUID cartItemId);

  CartResponse getCart(UUID userId);

  void clearCart(UUID userId);
}
