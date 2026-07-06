package com.ecommerce.stockservice.service;

import com.ecommerce.stockservice.model.Product;
import com.ecommerce.stockservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final ProductRepository productRepository;

    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
