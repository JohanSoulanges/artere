package com.johan.artere.controller;

import com.johan.artere.service.CartService;
import com.johan.artere.dto.CartDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Supprimer le panier")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}
