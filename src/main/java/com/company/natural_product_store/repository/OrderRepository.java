package com.company.natural_product_store.repository;

import com.company.natural_product_store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByAppUser_Id(Long userId);

    Optional<Order> findOrderByAppUser_IdAndId(Long userId, Long orderId);
}
