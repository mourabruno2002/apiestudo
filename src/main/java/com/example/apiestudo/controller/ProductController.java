package com.example.apiestudo.controller;

import com.example.apiestudo.dto.product.*;
import com.example.apiestudo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO productRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(Pageable pageable) {

        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO) {

        return ResponseEntity.ok(productService.update(id, productUpdateDTO));
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<ProductResponseDTO> updateActive(@PathVariable Long id, @Valid @RequestBody ProductActiveDTO productActiveDTO) {

        return ResponseEntity.ok(productService.updateActive(id, productActiveDTO));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateStock(@PathVariable Long id, @Valid @RequestBody ProductStockDTO productStockDTO) {

        return ResponseEntity.ok(productService.updateStock(id, productStockDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
