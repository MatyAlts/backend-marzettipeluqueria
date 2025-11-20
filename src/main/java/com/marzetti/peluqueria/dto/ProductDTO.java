package com.marzetti.peluqueria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private String imageUrl;
    private Double rating;
    private String description;
    private String ingredients;
    private String howToUse;
    private Integer stock;
    private List<String> badges;
    private String category;
    private Long categoryId;
    
    // Helper method to convert comma-separated badges to list
    public void setBadgesFromString(String badgesStr) {
        if (badgesStr != null && !badgesStr.trim().isEmpty()) {
            this.badges = Arrays.asList(badgesStr.split(","));
        } else {
            this.badges = List.of();
        }
    }
    
    // Helper method to convert list to comma-separated string
    public String getBadgesAsString() {
        if (badges != null && !badges.isEmpty()) {
            return String.join(",", badges);
        }
        return "";
    }
}
