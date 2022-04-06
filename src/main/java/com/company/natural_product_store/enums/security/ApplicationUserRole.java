package com.company.natural_product_store.enums.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.company.natural_product_store.enums.security.ApplicationUserPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    BASIC_USER(Sets.newHashSet(PRODUCT_READ, ORDER_READ, PAYMENT_READ)),
    ADMIN(Sets.newHashSet(ORDER_READ, ORDER_WRITE, USER_READ, USER_WRITE, PAYMENT_READ, PAYMENT_WRITE, PRODUCT_WRITE, PRODUCT_READ, PRODUCT_DELETE)),
    MANUFACTURER(Sets.newHashSet(ORDER_READ, USER_READ, PRODUCT_WRITE, PRODUCT_READ)),
    UNSPECIFIED(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> _permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        _permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return _permissions;
    }
}
