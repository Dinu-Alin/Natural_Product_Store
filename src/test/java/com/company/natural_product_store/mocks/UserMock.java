package com.company.natural_product_store.mocks;

import com.company.natural_product_store.entity.AppUser;
import com.company.natural_product_store.enums.security.ApplicationUserRole;

import java.time.LocalDateTime;

public class UserMock {

    private static final String BASIC_PASSWORD = "1q2w3e";
    public static AppUser getMockUser(String username, ApplicationUserRole applicationUserRole) {

        return AppUser.builder()
                .username(username)
                .lastName("last")
                .firstName("first")
                .email("exemple@exemple.com")
                .password(BASIC_PASSWORD)
                .telephone("0722222222")
                .address("anywhere")
                .createdAt(LocalDateTime.now())
                .role(applicationUserRole)
                .id(10L)
                .build();
    }
}
