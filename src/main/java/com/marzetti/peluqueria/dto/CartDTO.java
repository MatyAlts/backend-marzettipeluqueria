package com.marzetti.peluqueria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private List<CartItemDTO> items;
    private Double total;
    private Integer itemCount;
}
