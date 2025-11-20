package com.marzetti.peluqueria.service;

import com.marzetti.peluqueria.dto.ProductDTO;
import com.marzetti.peluqueria.entity.Category;
import com.marzetti.peluqueria.entity.Product;
import com.marzetti.peluqueria.repository.CategoryRepository;
import com.marzetti.peluqueria.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    public List<ProductDTO> getAllProducts(Long categoryId, Double minPrice, Double maxPrice, String search) {
        List<Product> products = productRepository.findByFilters(categoryId, minPrice, maxPrice, search);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToDTO(product);
    }
    
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setRating(productDTO.getRating() != null ? productDTO.getRating() : 0.0);
        product.setDescription(productDTO.getDescription());
        product.setIngredients(productDTO.getIngredients());
        product.setHowToUse(productDTO.getHowToUse());
        product.setStock(productDTO.getStock() != null ? productDTO.getStock() : 0);
        product.setBadges(productDTO.getBadgesAsString());
        product.setCategory(category);
        
        Product saved = productRepository.save(product);
        return convertToDTO(saved);
    }
    
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        
        if (productDTO.getName() != null) product.setName(productDTO.getName());
        if (productDTO.getBrand() != null) product.setBrand(productDTO.getBrand());
        if (productDTO.getPrice() != null) product.setPrice(productDTO.getPrice());
        if (productDTO.getImageUrl() != null) product.setImageUrl(productDTO.getImageUrl());
        if (productDTO.getRating() != null) product.setRating(productDTO.getRating());
        if (productDTO.getDescription() != null) product.setDescription(productDTO.getDescription());
        if (productDTO.getIngredients() != null) product.setIngredients(productDTO.getIngredients());
        if (productDTO.getHowToUse() != null) product.setHowToUse(productDTO.getHowToUse());
        if (productDTO.getStock() != null) product.setStock(productDTO.getStock());
        if (productDTO.getBadges() != null) product.setBadges(productDTO.getBadgesAsString());
        
        Product updated = productRepository.save(product);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setRating(product.getRating());
        dto.setDescription(product.getDescription());
        dto.setIngredients(product.getIngredients());
        dto.setHowToUse(product.getHowToUse());
        dto.setStock(product.getStock());
        dto.setBadgesFromString(product.getBadges());
        dto.setCategory(product.getCategory().getName());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
}
