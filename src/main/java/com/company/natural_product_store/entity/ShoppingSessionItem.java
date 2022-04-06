package com.company.natural_product_store.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class ShoppingSessionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long quantity = 0L;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt;

    @NotNull
    @OneToOne(mappedBy = "shoppingSessionItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product product;

    @NotNull
    @ManyToOne(optional = false)
    private ShoppingSession shoppingSession;
}
