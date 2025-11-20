package com.marzetti.peluqueria.controller;

import com.marzetti.peluqueria.dto.AddToCartRequest;
import com.marzetti.peluqueria.dto.CartDTO;
import com.marzetti.peluqueria.dto.CartItemDTO;
import com.marzetti.peluqueria.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {
    
    private final CartService cartService;
    
    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestHeader("X-Session-Id") String sessionId) {
        CartDTO cart = cartService.getCart(sessionId);
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/items")
    public ResponseEntity<CartItemDTO> addToCart(
            @RequestHeader("X-Session-Id") String sessionId,
            @Valid @RequestBody AddToCartRequest request
    ) {
        CartItemDTO cartItem = cartService.addToCart(sessionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }
    
    @PutMapping("/items/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {
        CartItemDTO updated = cartService.updateCartItem(id, quantity);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-Session-Id") String sessionId) {
        cartService.clearCart(sessionId);
        return ResponseEntity.noContent().build();
    }
}
