package com.example.productapi.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(Long id, String name, String sku, String category,
                              BigDecimal price,
                              Integer stockQuantity, ProductStatus status,
                              LocalDateTime createdAt) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getSku(), p.getCategory(), p.getPrice(), p.getStockQuantity(), p.getStatus(), p.getCreatedAt());
    }
}
