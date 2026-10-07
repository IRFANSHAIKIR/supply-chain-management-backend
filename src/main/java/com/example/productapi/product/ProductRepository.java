package com.example.productapi.product;

import org.springframework.data.jpa.repository.*;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsBySkuIgnoreCase(String sku);
}
