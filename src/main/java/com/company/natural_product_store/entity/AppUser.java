package com.company.natural_product_store.entity;

import com.company.natural_product_store.enums.security.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "User username, min 5 ch, max 30 ch", example = "MyUsername", required = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    @Size(min = 6, max = 250)
    @ApiModelProperty(notes = "User password name, min 6 ch, max 250 ch, !ENCRYPTED!", example = "password123", required = true)
    private String password;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp="^[A-Za-z0-9+_.-]+@(.+)$")
    @ApiModelProperty(notes = "User email", example = "example@example.com", required = true)
    private String email;

    @Transient
    private String passwordConfirm;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    @ApiModelProperty(notes = "User first name, min 1 ch", example = "Alin", required = true)
    private String firstName;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    @ApiModelProperty(notes = "User last name, min 1 ch", example = "Dinu", required = true)
    private String lastName;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "User address", example = "Brasov, 34D", required = true)
    private String address;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "User telephone", example = "+40712345678 or 0712345678", required = true)
    @Pattern(regexp="^(\\+4|)?(07[0-9]{2}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}$")
    private String telephone;

    @NotNull
    @Column(nullable = false)
    @ApiModelProperty(notes = "User creation time, optional", example = "07/04/2022", required = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ApiModelProperty(notes = "User last modification time, optional", example = "07/04/2022", required = false)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role  = ApplicationUserRole.UNSPECIFIED;

    @JsonIgnore
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private ShoppingSession shoppingSession;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Order> order = new ArrayList<>();
}
