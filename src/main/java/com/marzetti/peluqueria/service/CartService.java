package com.marzetti.peluqueria.service;

import com.marzetti.peluqueria.dto.AddToCartRequest;
import com.marzetti.peluqueria.dto.CartDTO;
import com.marzetti.peluqueria.dto.CartItemDTO;
import com.marzetti.peluqueria.dto.ProductDTO;
import com.marzetti.peluqueria.entity.CartItem;
import com.marzetti.peluqueria.entity.Product;
import com.marzetti.peluqueria.repository.CartItemRepository;
import com.marzetti.peluqueria.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    
    public CartDTO getCart(String sessionId) {
        List<CartItem> cartItems = cartItemRepository.findBySessionId(sessionId);
        List<CartItemDTO> itemDTOs = cartItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        double total = itemDTOs.stream()
                .mapToDouble(CartItemDTO::getSubtotal)
                .sum();
        
        int itemCount = itemDTOs.stream()
                .mapToInt(CartItemDTO::getQuantity)
                .sum();
        
        return new CartDTO(itemDTOs, total, itemCount);
    }
    
    @Transactional
    public CartItemDTO addToCart(String sessionId, AddToCartRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        
        // Check if product already in cart
        var existingItem = cartItemRepository.findBySessionIdAndProductId(sessionId, request.getProductId());
        
        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            
            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Insufficient stock");
            }
            
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setSessionId(sessionId);
        }
        
        CartItem saved = cartItemRepository.save(cartItem);
        return convertToDTO(saved);
    }
    
    @Transactional
    public CartItemDTO updateCartItem(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        
        if (cartItem.getProduct().getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        cartItem.setQuantity(quantity);
        CartItem updated = cartItemRepository.save(cartItem);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepository.deleteById(cartItemId);
    }
    
    @Transactional
    public void clearCart(String sessionId) {
        cartItemRepository.deleteBySessionId(sessionId);
    }
    
    private CartItemDTO convertToDTO(CartItem cartItem) {
        ProductDTO productDTO = productService.getProductById(cartItem.getProduct().getId());
        double subtotal = cartItem.getProduct().getPrice() * cartItem.getQuantity();
        
        return new CartItemDTO(
                cartItem.getId(),
                productDTO,
                cartItem.getQuantity(),
                subtotal
        );
    }
}
