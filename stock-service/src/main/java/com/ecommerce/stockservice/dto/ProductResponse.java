package com.ecommerce.stockservice.dto;

import com.ecommerce.stockservice.model.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price, int availableQuantity ) {

    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getAvailableQuantity());
    }
}
