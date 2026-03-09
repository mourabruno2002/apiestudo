package com.example.apiestudo.specification;

import com.example.apiestudo.enums.UserRole;
import com.example.apiestudo.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> nameContaining(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
    }

    public static Specification<User> isActive(Boolean active) {
        if (active == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), active);
    }

    public static Specification<User> hasRole(UserRole role) {
        if (role == null) {
            return null;
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role"), role);
    }
}
