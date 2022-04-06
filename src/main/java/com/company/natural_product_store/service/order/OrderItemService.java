package com.company.natural_product_store.service.order;

import com.company.natural_product_store.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> findAllByOrderId(Long orderId);
}
