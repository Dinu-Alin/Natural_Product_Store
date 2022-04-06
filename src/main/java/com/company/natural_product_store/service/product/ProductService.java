package com.company.natural_product_store.service.product;

import com.company.natural_product_store.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product);

    void deleteById(Long id);

    Product findProductByName(String name);

    void deleteAll();
}
