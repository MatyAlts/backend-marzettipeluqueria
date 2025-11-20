package com.marzetti.peluqueria.repository;

import com.marzetti.peluqueria.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findBySessionId(String sessionId);
    
    List<CartItem> findByUserId(Long userId);
    
    Optional<CartItem> findBySessionIdAndProductId(String sessionId, Long productId);
    
    void deleteBySessionId(String sessionId);
    
    void deleteByUserId(Long userId);
}
