package com.company.natural_product_store.service.user;

import com.company.natural_product_store.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    List<AppUser> findAll();

    AppUser save(AppUser user);

    void deleteById(Long id);

    void deleteAll();

    AppUser findByUsername(String username);

    UserDetails loadUserByUsername(String username);
}
