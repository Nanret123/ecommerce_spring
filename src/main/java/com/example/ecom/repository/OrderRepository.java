package com.example.ecom.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ecom.model.Order;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order>  {
  Optional<Order> findByOrderNumber(String orderNumber);
  
  //find all orders for a specific user
  List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);

  @Query("SELECT COUNT(o) from Order o WHERE o.userId = :userId")
  Long countOrdersByUserId(@Param("userId") UUID userId);
  
}
