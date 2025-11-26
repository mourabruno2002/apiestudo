package com.example.apiestudo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apiestudo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
