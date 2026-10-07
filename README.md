# Product API - Spring Boot + PostgreSQL

Complete backend for a Supply Chain product listing/create flow.

## Stack
Java 17, Spring Boot, Spring MVC, Bean Validation, Spring Data JPA, Hibernate, PostgreSQL, Docker.

## Run
```bash
docker compose up -d
mvn spring-boot:run
```

## Create
POST http://localhost:8080/api/products
```json
{"name":"MacBook Pro","sku":"MBP-001","category":"Electronics","price":1999.99,"stockQuantity":25,"status":"ACTIVE"}
```

## List
GET `/api/products?page=0&size=20`

Sort: `?sort=price,desc`
Search: `?search=mac`
Filter: `?category=Electronics&status=ACTIVE&minPrice=100&maxPrice=2000&minStock=5&maxStock=100`
Combine all of them freely.

Sort fields are whitelisted in the service to prevent invalid property access.

The list uses `JpaSpecificationExecutor` so optional filters are composed dynamically.
