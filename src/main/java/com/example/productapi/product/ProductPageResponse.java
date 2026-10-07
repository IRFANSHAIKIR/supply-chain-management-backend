package com.example.productapi.product;

import org.springframework.data.domain.Page;

import java.util.List;

public record ProductPageResponse(List<ProductResponse> content, int page, int size,
                                  long totalElements, int totalPages,
                                  boolean first, boolean last) {
    public static ProductPageResponse from(Page<Product> p) {
        return new ProductPageResponse(p.getContent().stream().map(ProductResponse::from).toList(), p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages(), p.isFirst(), p.isLast());
    }
}
