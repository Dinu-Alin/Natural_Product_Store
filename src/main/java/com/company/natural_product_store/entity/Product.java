package com.company.natural_product_store.entity;

import com.company.natural_product_store.enums.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String name;

    @Size(min = 5, max = 250)
    private String description;

    @NotNull
    @Column(nullable = false)
    private Double price = 0D;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category = Category.OTHER;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @OneToOne
    private ShoppingSessionItem shoppingSessionItem;

    @OneToOne
    private OrderItem orderItem;
}
