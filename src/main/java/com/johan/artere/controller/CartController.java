package com.johan.artere.controller;

import com.johan.artere.service.CartService;
import com.johan.artere.dto.CartDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Tag(name = "Panier")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Créer un panier")
    @PostMapping
    public ResponseEntity<CartDto.Response> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart());
    }

    @Operation(summary = "Voir le panier")
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto.Response> getCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @Operation(summary = "Ajouter un produit au panier")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = """
            {
              "productId": 1,
              "quantity": 2
            }
        """)))
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDto.Response> addItem(@PathVariable Long cartId,
                                                     @Valid @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(cartId, request));
    }

    @Operation(summary = "Modifier la quantité d'un article")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(examples = @ExampleObject(value = """
            {
              "productId": 1,
              "quantity": 5
            }
        """)))
    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartDto.Response> updateItem(@PathVariable Long cartId,
                                                        @PathVariable Long itemId,
                                                        @Valid @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(cartService.updateItem(cartId, itemId, request));
    }

    @Operation(summary = "Supprimer un article du panier")
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<CartDto.Response> removeItem(@PathVariable Long cartId,
                                                        @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(cartId, itemId));
    }

    @Operation(summary = "Vider le panier")
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<CartDto.Response> clearCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.clearCart(cartId));
    }

    @Operation(summary = "Supprimer le panier")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
