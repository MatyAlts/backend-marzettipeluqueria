package com.marzetti.peluqueria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "image_url", length = 1000)
    private String imageUrl;
    
    @Column(nullable = false)
    private Double rating = 0.0;
    
    @Column(length = 2000)
    private String description;
    
    @Column(length = 1000)
    private String ingredients;
    
    @Column(name = "how_to_use", length = 1000)
    private String howToUse;
    
    @Column(nullable = false)
    private Integer stock = 0;
    
    // Store badges as comma-separated values
    @Column(length = 500)
    private String badges;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
