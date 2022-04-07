package com.company.natural_product_store.controller;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Navigation Controller", tags = "")
@RestController
public class PageSimulationController {

    @GetMapping("/home")
    public String home(){
        return "This is Home Page";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(){
        return "This is Admin Page";
    }
}
