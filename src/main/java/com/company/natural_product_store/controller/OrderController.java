package com.company.natural_product_store.controller;

import com.company.natural_product_store.entity.AppUser;
import com.company.natural_product_store.entity.Order;
import com.company.natural_product_store.entity.OrderItem;
import com.company.natural_product_store.entity.PaymentDetails;
import com.company.natural_product_store.exception.InvalidDataException;
import com.company.natural_product_store.exception.InvalidIdException;
import com.company.natural_product_store.service.order.OrderItemService;
import com.company.natural_product_store.service.order.OrderService;
import com.company.natural_product_store.service.payment.PaymentService;
import com.company.natural_product_store.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(value = "Orders Controller", tags = "/orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final OrderItemService orderItemService;

    //region Payment
    @GetMapping("/{orderId}/payment")
    @PreAuthorize("hasAnyAuthority('payment:read')")
    public ResponseEntity<PaymentDetails> getPaymentByOrder(@PathVariable Long orderId, Principal principal) {

        if(orderId <= 0)
        {
            throw new InvalidIdException("Id Invalid");
        }
        AppUser user = userService.findByUsername(principal.getName());

        Order order = orderService.findOrderByUserIdWhereOrderId(user.getId(), orderId);
        PaymentDetails paymentDetails = paymentService.findPaymentByOrderId(order.getId());

        return ResponseEntity.ok(paymentDetails);
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<PaymentDetails> createPayment(@PathVariable Long orderId,
                                                        @RequestBody PaymentDetails paymentDetails){
        if(orderId <= 0)
        {
            throw new InvalidIdException("Id Invalid");
        }
        return ResponseEntity.ok(paymentService.save(paymentDetails));
    }
    //endregion

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {

        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<List<OrderItem>> getAllOrderItems(@PathVariable Long orderId, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        List<Order> orders = orderService.findAllById(user.getId());

        var hasWantedOrder = orders.stream().anyMatch(order -> order.getId().equals(orderId));

        if(!hasWantedOrder){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<OrderItem> orderItems = orderItemService.findAllByOrderId(orderId);

        return ResponseEntity.ok(orderItems);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<List<Order>> getAllOrdersByUserId(Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        List<Order> orders = orderService.findAllById(user.getId());

        return ResponseEntity.ok(orders);
    }

    @Validated
    @PostMapping()
    @PreAuthorize("hasAuthority('order:write')")
    @Transactional
    public ResponseEntity<Order> createOrder(@RequestBody() @ApiParam(value = "OrderDetails", required = true) Order order,
                                                    BindingResult bindingResult,
                                                    Principal principal) {
        System.out.println("writeOrder");

        if (bindingResult.hasErrors()) {
            throw new InvalidDataException("Invalid Order");
        }

        if (order.getAppUser() == null) {
            AppUser user = userService.findByUsername(principal.getName());
            order.setAppUser(user);
        }

        PaymentDetails paymentDetails = new PaymentDetails();
        if(order.getPaymentDetails() == null)
        {
            paymentDetails = paymentService.save(new PaymentDetails());
            order.setPaymentDetails(paymentDetails);

        }

        Order newOrderDetails = orderService.save(order);

        if(newOrderDetails != null)
        {
            paymentDetails.setOrder(newOrderDetails);
            paymentService.save(paymentDetails);
        }

        return ResponseEntity.ok(newOrderDetails);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<Order> addOrderItemToOrder(@PathVariable Long orderId, @RequestBody @ApiParam Map<String, Object> requestBody) {
        Objects.requireNonNull(orderId);
        final ObjectMapper objectReader = new ObjectMapper();
        requestBody.put("id", orderId);

        try{
            final OrderItem orderItem = objectReader.convertValue(requestBody, OrderItem.class);
            Order order = orderService.findById(orderId);

            order.getOrderItems().add(orderItem);

            return ResponseEntity.ok(orderService.save(order));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId){
        orderService.deleteById(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('order:write')")
    public ResponseEntity<Order> deleteAllOrders(Principal principal){
        AppUser user = userService.findByUsername(principal.getName());
        List<Order> orders = orderService.findAllById(user.getId());

        orders.forEach(order -> orderService.deleteById(order.getId()));

        return ResponseEntity.ok().build();
    }
}
