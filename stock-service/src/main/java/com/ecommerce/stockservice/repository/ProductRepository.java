package com.ecommerce.stockservice.repository;

import com.ecommerce.stockservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
