package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.CreateOrderRequest;
import com.ecommerce.orderservice.event.OrderCreatedEvent;
import com.ecommerce.orderservice.messaging.OrderEventPublisher;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderStatus;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    public Order createOrder(Long userId, CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(request.productId());
        order.setQuantity(request.quantity());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(new Date().toInstant());
        Order savedOrder = orderRepository.save(order);
        orderEventPublisher.publishOrderCreated(OrderCreatedEvent.fromOrder(savedOrder));

        return savedOrder;
    }

    public List<Order> getAllOrdersForUser(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
