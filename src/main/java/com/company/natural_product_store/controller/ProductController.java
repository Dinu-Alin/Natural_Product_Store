package com.company.natural_product_store.controller;

import com.company.natural_product_store.entity.Product;
import com.company.natural_product_store.exception.InvalidDataException;
import com.company.natural_product_store.service.product.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Api(value = "Products Controller", tags = "/products")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @PreAuthorize("hasAuthority('product:read')")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info(this.getClass().getName()," GET ALL products");

        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{productName}")
    public ResponseEntity<Product> getProductByName(@PathVariable @Valid @NotNull @Size(min = 3) String productName, BindingResult bindingResult) {

        log.info(this.getClass().getName()," GET product by PATH_V productName");

        if(bindingResult.hasErrors())
        {
            throw new InvalidDataException("Invalid product name");
        }

        return ResponseEntity.ok(productService.findProductByName(productName));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('product:write')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info(this.getClass().getName()," POST product, REQUEST_B product");

        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('product:delete')")
    public ResponseEntity deleteAll() {
        log.info(this.getClass().getName()," DELETE ALL products");

        productService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('product:delete')")
    public ResponseEntity<?> deleteById(@PathVariable Long productId) {
        log.info(this.getClass().getName()," DELETE by PATH_V productId");

        productService.deleteById(productId);
        return ResponseEntity.ok().build();
    }
}
