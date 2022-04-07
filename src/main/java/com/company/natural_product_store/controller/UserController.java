package com.company.natural_product_store.controller;

import com.company.natural_product_store.entity.AppUser;
import com.company.natural_product_store.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Api(value = "Users Controller", tags = "/users")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getAllUsers(Principal principal) {
        log.info(this.getClass().getName()," GET ALL users ---- ROLE: ADMIN");


        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        log.info(this.getClass().getName()," POST user,  REQUEST_B user");
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> deleteAll() {
        log.info(this.getClass().getName()," DELETE ALL users");

        userService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> deleteById(@PathVariable Long userId) {
        log.info(this.getClass().getName()," DELETE by PATH_V userId");

        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }
}
