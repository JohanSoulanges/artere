package com.johan.artere.controller;

import com.johan.artere.dto.CategoryDto;
import com.johan.artere.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Catégories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Lister toutes les catégories")
    @GetMapping
    public ResponseEntity<List<CategoryDto.Summary>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Arbre complet du catalogue")
    @GetMapping("/tree")
    public ResponseEntity<List<CategoryDto.Response>> getTree() {
        return ResponseEntity.ok(categoryService.getRootCategories());
    }

    @Operation(summary = "Obtenir une catégorie par ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Créer une catégorie")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
            {
              "name": "Électronique",
              "description": "Tous les produits électroniques",
              "parentId": null
            }
        """)))
    @PostMapping
    public ResponseEntity<CategoryDto.Response> create(@Valid @RequestBody CategoryDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @Operation(summary = "Modifier une catégorie")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
            {
              "name": "Électronique",
              "description": "Tous les produits électroniques",
              "parentId": null
            }
        """)))
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto.Response> update(@PathVariable Long id,
                                                       @Valid @RequestBody CategoryDto.Request request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @Operation(summary = "Supprimer une catégorie")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lier une catégorie à un parent")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = """
            {
              "name": "Électronique",
              "description": "Tous les produits électroniques",
              "parentId": null
            }
        """)))
    @PutMapping("/{id}/parent/{parentId}")
    public ResponseEntity<CategoryDto.Response> linkParent(@PathVariable Long id,
                                                           @PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.linkParent(id, parentId));
    }

    @Operation(summary = "Délier une catégorie de son parent")
    @DeleteMapping("/{id}/parent")
    public ResponseEntity<CategoryDto.Response> unlinkParent(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.unlinkParent(id));
    }
}