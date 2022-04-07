package com.company.natural_product_store.mocks;

import com.company.natural_product_store.entity.Order;
import com.company.natural_product_store.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderMock {

    public static Order getMockedProduct() {
        return Order.builder()
                .id(1L)
                .total(20.0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static List<Product> getProducts() {
        return List.of(
                Product.builder()
                        .name("Black")
                        .price(12.0)
                        .build(),
                Product.builder()
                        .name("Aeris Pucitae")
                        .price(15.0)
                        .build()
        );
    }

    public static List<List<Product>> getProductsList() {
        return List.of(
                getProducts()
        );
    }
}
