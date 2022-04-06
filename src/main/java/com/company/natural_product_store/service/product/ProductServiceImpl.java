package com.company.natural_product_store.service.product;

import com.company.natural_product_store.entity.Product;
import com.company.natural_product_store.exception.DataNotFoundException;
import com.company.natural_product_store.exception.InvalidDataException;
import com.company.natural_product_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Product save(Product product) {
        try {
            if (product.getCreatedAt() == null) {
                product.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            }
            return productRepository.save(product);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid product!");
        }
    }

    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException("Data not found!");
        }
    }

    @Override
    public Product findProductByName(String name) {
        Product product = productRepository.findProductByName(name).orElseThrow(() ->
                new DataNotFoundException("Could not find product with name: {}".formatted(name))
        );

        return product;
    }

    public void deleteAll() {
        try {
            productRepository.deleteAll();
        } catch (Exception e) {
            throw new DataNotFoundException("Data not found!");
        }
    }
}
