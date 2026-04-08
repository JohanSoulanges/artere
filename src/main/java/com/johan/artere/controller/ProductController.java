package com.johan.artere.controller;

import com.johan.artere.dto.ProductDto;
import com.johan.artere.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto.Response>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto.Response>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<ProductDto.Response> create(@Valid @RequestBody ProductDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto.Response> update(@PathVariable Long id,
                                                       @Valid @RequestBody ProductDto.Request request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/category/{categoryId}")
    public ResponseEntity<ProductDto.Response> linkToCategory(@PathVariable Long id,
                                                               @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.linkToCategory(id, categoryId));
    }

    @DeleteMapping("/{id}/category")
    public ResponseEntity<ProductDto.Response> unlinkFromCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.unlinkFromCategory(id));
    }
}
