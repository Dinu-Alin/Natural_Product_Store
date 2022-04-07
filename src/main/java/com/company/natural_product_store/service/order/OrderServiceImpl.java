package com.company.natural_product_store.service.order;

import com.company.natural_product_store.entity.Order;
import com.company.natural_product_store.exception.DataNotFoundException;
import com.company.natural_product_store.exception.InvalidDataException;
import com.company.natural_product_store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllById(Long userId) {
        return orderRepository.findAllByAppUser_Id(userId);
    }

    @Override
    public Order findById(Long orderId) {
        try {
            return orderRepository.findById(orderId).orElseThrow(
                    () -> new DataNotFoundException(
                            MessageFormat.format("Could not find order with order ID: {0}", orderId))
            );
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Invalid order!");
        }
    }

    @Override
    public Order save(Order order) {
        try {
            if (order.getCreatedAt() == null) {
                order.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            }
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new InvalidDataException("Invalid order!");
        }
    }

    @Override
    public Order findOrderByUserIdWhereOrderId(Long userId, Long orderId) {
        try {
            return orderRepository.findOrderByAppUser_IdAndId(userId, orderId).orElseThrow(
                    () -> new DataNotFoundException(
                            MessageFormat.format("Could not find order with order ID: {0} of user with ID: {1}", orderId, userId))
            );
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidDataException("Invalid order!");
        }
    }

    @Override
    public void deleteById(Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new DataNotFoundException("Invalid order!");
        }
    }

    @Override
    public void deleteAll() {
        try {
            orderRepository.deleteAll();
        } catch (Exception e) {
            throw new InvalidDataException("Invalid request!");
        }
    }
}
