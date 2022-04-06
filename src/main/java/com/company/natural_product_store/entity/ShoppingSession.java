package com.company.natural_product_store.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class ShoppingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double total = 0D;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt;

    @NotNull
    @OneToOne
    private AppUser appUser;

    @OneToMany(mappedBy = "shoppingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingSessionItem> shoppingSessionItems = new ArrayList<>();
}