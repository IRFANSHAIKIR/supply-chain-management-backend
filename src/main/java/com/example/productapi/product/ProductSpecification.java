package com.example.productapi.product;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class ProductSpecification {
    private ProductSpecification() {
    }

    public static Specification<Product> category(String v) {
        return (r, q, c) -> c.equal(c.lower(r.get("category")), v.toLowerCase());
    }

    public static Specification<Product> status(ProductStatus v) {
        return (r, q, c) -> c.equal(r.get("status"), v);
    }

    public static Specification<Product> search(String v) {
        return (r, q, c) -> {
            String p = "%" + v.toLowerCase() + "%";
            return c.or(c.like(c.lower(r.get("name")), p), c.like(c.lower(r.get("sku")), p));
        };
    }

    public static Specification<Product> minPrice(BigDecimal v) {
        return (r, q, c) -> c.greaterThanOrEqualTo(r.get("price"), v);
    }

    public static Specification<Product> maxPrice(BigDecimal v) {
        return (r, q, c) -> c.lessThanOrEqualTo(r.get("price"), v);
    }

    public static Specification<Product> minStock(Integer v) {
        return (r, q, c) -> c.greaterThanOrEqualTo(r.get("stockQuantity"), v);
    }

    public static Specification<Product> maxStock(Integer v) {
        return (r, q, c) -> c.lessThanOrEqualTo(r.get("stockQuantity"), v);
    }
}
