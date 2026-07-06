package com.ecommerce.stockservice.controller;

import com.ecommerce.stockservice.dto.ProductResponse;
import com.ecommerce.stockservice.model.Product;
import com.ecommerce.stockservice.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return this.stockService.getAllProducts().stream()
                .map(ProductResponse::fromProduct)
                .toList();
    }
}
