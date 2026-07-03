package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.CreateOrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public OrderResponse createOrder(@AuthenticationPrincipal String userId, @Valid @RequestBody CreateOrderRequest orderRequest) {
        return OrderResponse.fromOrder(orderService.createOrder(Long.valueOf(userId), orderRequest));
    }

    @GetMapping
    public List<OrderResponse> getUserOrders(@AuthenticationPrincipal String userId) {
        return orderService.getAllOrdersForUser(Long.valueOf(userId)).stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }
}
