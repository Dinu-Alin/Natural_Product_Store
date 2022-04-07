package com.company.natural_product_store.service.order;

import com.company.natural_product_store.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    List<Order> findAllById(Long userId);

    Order findById(Long orderId);

    Order save(Order order);

    Order findOrderByUserIdWhereOrderId(Long userId, Long OrderId);

    void deleteById(Long orderId);

    void deleteAll();
}
