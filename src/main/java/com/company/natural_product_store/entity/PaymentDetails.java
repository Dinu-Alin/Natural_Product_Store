package com.company.natural_product_store.entity;

import com.company.natural_product_store.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long amount = 0L;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(nullable = false)
    private String provider = "Unspecified";

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.UNSPECIFIED;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt;

    @OneToOne(fetch = FetchType.EAGER)
    private Order order;
}
