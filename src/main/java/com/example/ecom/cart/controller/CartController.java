package com.example.ecom.cart.controller;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.payload.response.Result;
import com.example.ecom.cart.dtos.AddToCartRequest;
import com.example.ecom.cart.dtos.CartResponse;
import com.example.ecom.cart.dtos.UpdateCartItemRequest;
import com.example.ecom.cart.service.CartService;
import com.example.ecom.security.services.UserDetailsImpl;
import com.example.ecom.utils.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "Cart management APIs")
@RequiredArgsConstructor
@SecurityRequirement(name = "apiBearerAuth")
public class CartController {

  private final CartService service;

  @PostMapping("/add")
  @Operation(summary = "Add product to cart")
  public Result addToCart(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @Valid @RequestBody AddToCartRequest request) {
    UUID userId = userDetails.getId();
    CartResponse response = service.addToCart(userId, request);
    return ResponseUtil.success("Product added to cart successfully", response);
  }

  @GetMapping
  @Operation(summary = "Get cart")
  public Result getCartItems(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    UUID userId = userDetails.getId();
    CartResponse response = service.getCart(userId);
    return ResponseUtil.success("Cart items retrieved successfully", response);
  }

  @PutMapping("/update")
  @Operation(summary = "Update cart item quantity")
  public Result updateCartItem(
      @Valid @RequestBody UpdateCartItemRequest request) {
    CartResponse response = service.updateCartItem(request);
    return ResponseUtil.success("Cart item updated successfully", response);
  }

  @DeleteMapping("/remove/{cartItemId}")
  @Operation(summary = "Remove product from cart")
  public Result removeFromCart(
      @Parameter(description = "ID of the cart item to remove from cart", required = true) 
      @PathVariable UUID cartItemId) {
    service.removeCartItem(cartItemId);
    return ResponseUtil.success("Product removed from cart successfully", null);
  }

  @DeleteMapping("/clear")
  @Operation(summary = "Clear cart")
  public Result clearCart(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    UUID userId = userDetails.getId();
    service.clearCart(userId);
    return ResponseUtil.success("Cart cleared successfully", null);
  }
}
