package com.company.natural_product_store.service.payment;

import com.company.natural_product_store.entity.PaymentDetails;

import java.util.List;

public interface PaymentService {
    List<PaymentDetails> findAll();

    PaymentDetails save(PaymentDetails product);

    PaymentDetails findPaymentByOrderId(Long orderId);

    void deleteById(Long id);

    void deleteAll();
}
