package com.company.natural_product_store.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Payment Controller", tags = "/payment")
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
}
