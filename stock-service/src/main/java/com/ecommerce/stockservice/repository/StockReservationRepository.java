package com.ecommerce.stockservice.repository;

import com.ecommerce.stockservice.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockReservationRepository extends JpaRepository<StockReservation, Long> {
}
