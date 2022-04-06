package com.company.natural_product_store.enums.security;

public enum ApplicationUserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    PRODUCT_DELETE("payment:delete"),
    PAYMENT_READ("payment:read"),
    PAYMENT_WRITE("payment:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
