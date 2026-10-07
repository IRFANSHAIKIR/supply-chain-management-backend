package com.example.productapi.product;

import com.example.productapi.common.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private static final Set<String> SORT_FIELDS = Set.of("id", "name", "sku", "category", "price", "stockQuantity", "status", "createdAt");
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ProductResponse create(CreateProductRequest r) {
        if (repo.existsBySkuIgnoreCase(r.sku()))
            throw new BadRequestException("Product with SKU '" + r.sku() + "' already exists");
        Product p = new Product();
        p.setName(r.name().trim());
        p.setSku(r.sku().trim().toUpperCase());
        p.setCategory(r.category().trim());
        p.setPrice(r.price());
        p.setStockQuantity(r.stockQuantity());
        p.setStatus(r.status());
        return ProductResponse.from(repo.save(p));
    }

    public ProductResponse get(Long id) {
        return ProductResponse.from(repo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found")));
    }

    public ProductPageResponse list(int page, int size, String sort, String search, String category, ProductStatus status, BigDecimal minPrice, BigDecimal maxPrice, Integer minStock, Integer maxStock) {
        if (page < 0) throw new BadRequestException("page must be >= 0");
        if (size < 1 || size > 100)
            throw new BadRequestException("size must be between 1 and 100");
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0)
            throw new BadRequestException("minPrice cannot be greater than maxPrice");
        if (minStock != null && maxStock != null && minStock > maxStock)
            throw new BadRequestException("minStock cannot be greater than maxStock");
        Specification<Product> s = Specification.where(null);
        if (text(search)) s = s.and(ProductSpecification.search(search.trim()));
        if (text(category)) s = s.and(ProductSpecification.category(category.trim()));
        if (status != null) s = s.and(ProductSpecification.status(status));
        if (minPrice != null) s = s.and(ProductSpecification.minPrice(minPrice));
        if (maxPrice != null) s = s.and(ProductSpecification.maxPrice(maxPrice));
        if (minStock != null) s = s.and(ProductSpecification.minStock(minStock));
        if (maxStock != null) s = s.and(ProductSpecification.maxStock(maxStock));
        return ProductPageResponse.from(repo.findAll(s, PageRequest.of(page, size, parseSort(sort))));
    }

    private Sort parseSort(String sort) {
        if (!text(sort)) return Sort.by(Sort.Order.desc("createdAt"));
        String[] p = sort.split(",");
        if (p.length > 2)
            throw new BadRequestException("sort format must be field,asc or field,desc");
        String field = p[0].trim();
        if (!SORT_FIELDS.contains(field))
            throw new BadRequestException("Invalid sort field: " + field);
        Sort.Direction d = Sort.Direction.ASC;
        if (p.length == 2) try {
            d = Sort.Direction.fromString(p[1].trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("sort direction must be asc or desc");
        }
        return Sort.by(d, field);
    }

    private boolean text(String v) {
        return v != null && !v.trim().isEmpty();
    }
}
