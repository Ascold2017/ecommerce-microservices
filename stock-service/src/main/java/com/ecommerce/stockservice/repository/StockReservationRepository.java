package com.ecommerce.stockservice.repository;

import com.ecommerce.stockservice.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockReservationRepository extends JpaRepository<StockReservation, Long> {
    Optional<StockReservation> findByOrderId(Long orderId);
}
