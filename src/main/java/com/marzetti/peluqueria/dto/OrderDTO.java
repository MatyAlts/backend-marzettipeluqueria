package com.marzetti.peluqueria.dto;

import com.marzetti.peluqueria.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Double totalAmount;
    private Order.OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
}
