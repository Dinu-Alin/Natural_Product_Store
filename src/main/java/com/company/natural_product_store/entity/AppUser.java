package com.company.natural_product_store.entity;

import com.company.natural_product_store.enums.security.ApplicationUserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    @Size(min = 6, max = 250)
    private String password;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp="^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @Transient
    private String passwordConfirm;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    @Pattern(regexp="^(\\+4|)?(07[0-9]{2}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$")
    private String telephone;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role  = ApplicationUserRole.UNSPECIFIED;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private ShoppingSession shoppingSession;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Order> order = new ArrayList<>();
}
