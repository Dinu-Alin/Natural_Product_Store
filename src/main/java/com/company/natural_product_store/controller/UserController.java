package com.company.natural_product_store.controller;

import com.company.natural_product_store.entity.AppUser;
import com.company.natural_product_store.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Api(value = "Users Controller", tags = "/users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getAllUsers(Principal principal) {
        System.out.println("getUsers");


        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        System.out.println("writeUser");
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> deleteById(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
