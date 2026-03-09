package com.example.apiestudo.specification;

import com.example.apiestudo.model.Category;
import com.example.apiestudo.model.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> fetchCategory() {
        return ((root, query, criteriaBuilder) -> {

            if (query != null && query.getResultType() != Long.class) {
                root.fetch("category", JoinType.LEFT);
            }
            return criteriaBuilder.conjunction();
        });
    }

    public static Specification<Product> categoryNameContaining(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            return null;
        }

        return ((root, query, criteriaBuilder) -> {

            Join<Product, Category> join = root.join("category");

            return criteriaBuilder.like(criteriaBuilder.lower(join.get("name")), "%" + categoryName.toLowerCase().trim() + "%");
        });
    }

    public static Specification<Product> nameContaining(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%" + name.trim().toLowerCase() + "%");

    }

    public static Specification<Product> hasSku(String sku) {
        if (sku == null || sku.isBlank()) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal((root.get("sku")), sku.trim());
    }

    public static Specification<Product> isActive(Boolean active) {
        if (active == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), active);
    }

    public static Specification<Product> findByPrice(BigDecimal priceMin, BigDecimal priceMax) {
        if (priceMin == null && priceMax == null) {
            return null;
        }

        if (priceMin == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax);
        }

        if (priceMax == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin);
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), priceMin, priceMax);
    }
}
