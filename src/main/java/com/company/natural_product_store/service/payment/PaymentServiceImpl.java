package com.company.natural_product_store.service.payment;

import com.company.natural_product_store.entity.PaymentDetails;
import com.company.natural_product_store.exception.DataNotFoundException;
import com.company.natural_product_store.exception.InvalidDataException;
import com.company.natural_product_store.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    public List<PaymentDetails> findAll() {
        return paymentRepository.findAll();
    }

    public PaymentDetails save(PaymentDetails paymentDetails) {
        try {
            if (paymentDetails.getCreatedAt() == null) {
                paymentDetails.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            }
            return paymentRepository.saveAndFlush(paymentDetails);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid payment!");
        }
    }

    @Override
    public PaymentDetails findPaymentByOrderId(Long orderId) {
        PaymentDetails paymentDetails = paymentRepository.findPaymentDetailsByOrderId(orderId).orElseThrow(() ->
                new DataNotFoundException(MessageFormat.format("Could not find payment with order ID: {0}", orderId))
        );

        return paymentDetails;
    }

    public void deleteById(Long id) {
        try {
            paymentRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataNotFoundException("Data not found!");
        }
    }

    public void deleteAll() {
        try {
            paymentRepository.deleteAll();
        } catch (Exception e) {
            throw new DataNotFoundException("Data not found!");
        }
    }
}
