package com.example.ecom.cart.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecom.cart.dtos.AddToCartRequest;
import com.example.ecom.cart.dtos.CartMapper;
import com.example.ecom.cart.dtos.CartResponse;
import com.example.ecom.cart.dtos.UpdateCartItemRequest;
import com.example.ecom.cart.interfaces.ICart;
import com.example.ecom.model.Cart;
import com.example.ecom.model.CartItem;
import com.example.ecom.model.Product;
import com.example.ecom.repository.CartItemRepository;
import com.example.ecom.repository.CartRepository;
import com.example.ecom.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService implements ICart {

  private final CartRepository cartRepo;
  private final CartMapper cartMapper;
  private final ProductRepository productRepo;
  private final CartItemRepository cartItemRepo;

  @Override
  public CartResponse addToCart(UUID userId, AddToCartRequest request) {
    Cart cart = getOrCreateCart(userId);

    // Validate product exists
    Product product = productRepo.findById(request.getProductId())
        .orElseThrow(() -> new RuntimeException("Product not found"));

    // Check if product is already in the cart
    CartItem existingItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), request.getProductId())
        .orElse(null);

    if (existingItem != null) {
      // Update quantity if product already exists in the cart
      existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
      cartItemRepo.save(existingItem);
    } else {
      // Create new CartItem if product is not in the cart
      CartItem newItem = new CartItem();
      newItem.setCart(cart);
      newItem.setProduct(product);
      newItem.setQuantity(request.getQuantity());
      newItem.setPrice(product.getPrice());
      cartItemRepo.save(newItem);
      if(cart.getCartItems() == null) {
        cart.setCartItems(new ArrayList<>());
      }
      cart.getCartItems().add(newItem);
    }

    //refresh the cart to ensure it has the latest items
    cart = cartRepo.findById(cart.getId())
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    return cartMapper.toCartResponse(cart);
  }

  @Override
  public CartResponse updateCartItem(UpdateCartItemRequest request) {
    CartItem cartItem = cartItemRepo.findById(request.getCartItemId())
        .orElseThrow(() -> new RuntimeException("Cart item not found"));

    if(request.getQuantity() <= 0) {
      // If quantity is zero or less, remove the item from the cart
      cartItemRepo.delete(cartItem);
    }else{
      // Update the quantity of the cart item
      cartItem.setQuantity(request.getQuantity());
      cartItemRepo.save(cartItem);
    }

    // Refresh the cart to get the latest items
    Cart cart = cartRepo.findById(cartItem.getCart().getId())
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    
    return cartMapper.toCartResponse(cart);
  }

  @Override
  public void removeCartItem(UUID cartItemId) {
    CartItem cartItem = cartItemRepo.findById(cartItemId)
        .orElseThrow(() -> new RuntimeException("Cart item not found"));

    // Remove the cart item
    cartItemRepo.delete(cartItem);
  }

  @Override
  public CartResponse getCart(UUID userId) {
    Cart cart = cartRepo.findByUserIdWithItems(userId)
         .orElse(null);
    return cartMapper.toCartResponse(cart);
  }

  @Override
  public void clearCart(UUID userId) {
    Cart cart = cartRepo.findByUserId(userId)
        .orElseThrow(() -> new RuntimeException("Cart not found"));
    
    // Clear all items in the cart
    if (cart.getCartItems() != null) {
      cartItemRepo.deleteAll(cart.getCartItems());
      cart.getCartItems().clear();
      cartRepo.save(cart);
    }
    
    cartRepo.save(cart);
  }

  private Cart getOrCreateCart(UUID userId) {
    return cartRepo.findByUserId(userId)
        .orElseGet(() -> {
          Cart cart = new Cart();
          cart.setUserId(userId);
          cart.setCartItems(new ArrayList<>());
          return cartRepo.save(cart);
        });

  }
}
