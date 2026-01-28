package com.example.apiestudo.controller;

import com.example.apiestudo.dto.category.CategoryActiveDTO;
import com.example.apiestudo.dto.category.CategoryRequestDTO;
import com.example.apiestudo.dto.category.CategoryResponseDTO;
import com.example.apiestudo.dto.category.CategoryUpdateDTO;
import com.example.apiestudo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(categoryRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(Pageable pageable) {

        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {

        return ResponseEntity.ok(categoryService.update(id, categoryUpdateDTO));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<CategoryResponseDTO> updateActive(@PathVariable Long id, @Valid @RequestBody CategoryActiveDTO categoryActiveDTO) {

        return ResponseEntity.ok(categoryService.updateActive(id, categoryActiveDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
