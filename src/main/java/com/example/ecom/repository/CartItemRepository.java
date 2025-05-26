package com.example.ecom.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
Optional<CartItem> findByCartIdAndProductId(UUID cartId, UUID productId);

void deleteByCartIdAndProductId(UUID cartId, UUID productId);
   
  
}
