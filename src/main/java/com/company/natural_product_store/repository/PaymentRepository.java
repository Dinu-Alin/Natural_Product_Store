package com.company.natural_product_store.repository;

import com.company.natural_product_store.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
    Optional<PaymentDetails> findPaymentDetailsByOrderId(Long orderId);
}
