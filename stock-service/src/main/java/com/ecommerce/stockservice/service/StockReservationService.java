package com.ecommerce.stockservice.service;

import com.ecommerce.stockservice.event.OrderCreatedEvent;
import com.ecommerce.stockservice.event.PaymentFailedEvent;
import com.ecommerce.stockservice.event.StockReservationFailed;
import com.ecommerce.stockservice.event.StockReserved;
import com.ecommerce.stockservice.messaging.StockEventPublisher;
import com.ecommerce.stockservice.model.Product;
import com.ecommerce.stockservice.model.ReservationStatus;
import com.ecommerce.stockservice.model.StockReservation;
import com.ecommerce.stockservice.repository.ProductRepository;
import com.ecommerce.stockservice.repository.StockReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class StockReservationService {

    private final ProductRepository productRepository;
    private final StockReservationRepository reservationRepository;
    private final StockEventPublisher eventPublisher;

    public StockReservationService(ProductRepository productRepository,
                                   StockReservationRepository reservationRepository,
                                   StockEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
        this.eventPublisher = eventPublisher;
    }

    // @Transactional: списание остатка + запись резерва — одна локальная ACID-транзакция.
    // Либо оба изменения в БД, либо ни одного.
    @Transactional
    public void reserve(OrderCreatedEvent event) {
        Product product = productRepository.findById(event.productId())
                .orElseThrow(() -> new IllegalStateException("Нет товара id=" + event.productId()));


        if (product.getAvailableQuantity() < event.quantity()) {
            eventPublisher.publishStockReservationFailed(
                    new StockReservationFailed(event.orderId(), event.userId(),
                            "Недостаточно товара: есть " + product.getAvailableQuantity()
                                    + ", нужно " + event.quantity()));
            return;   // выходим штатно, без throw
        }
        product.setAvailableQuantity(product.getAvailableQuantity() - event.quantity());

        StockReservation reservation = new StockReservation();
        reservation.setOrderId(event.orderId());
        reservation.setProductId(event.productId());
        reservation.setQuantity(event.quantity());
        reservation.setStatus(ReservationStatus.RESERVED);
        reservationRepository.save(reservation);

        // сумму для платежа считает владелец цены — stock
        BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(event.quantity()));

        // публикуем ПОСЛЕ сохранения. Тонкость: если сервис упадёт между commit и этой
        // строкой — событие потеряется (dual-write problem). Пока живём с этим,
        // в 3d закроем через Transactional Outbox.
        eventPublisher.publishStockReserved(
                new StockReserved(event.orderId(), event.userId(),
                        event.productId(), event.quantity(), amount));
    }

    @Transactional
    public void release(PaymentFailedEvent event) {
        StockReservation reservation = reservationRepository.findByOrderId(event.orderId())
                .orElse(null);

        if (reservation == null || reservation.getStatus() != ReservationStatus.RESERVED) {
            return;
        }
        Product product = productRepository.findById(reservation.getProductId()).orElseThrow();
        product.setAvailableQuantity(product.getAvailableQuantity() + reservation.getQuantity());
        reservation.setStatus(ReservationStatus.RELEASED);
    }
}