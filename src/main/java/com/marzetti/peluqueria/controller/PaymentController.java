package com.marzetti.peluqueria.controller;

import com.marzetti.peluqueria.dto.PaymentPreferenceDTO;
import com.marzetti.peluqueria.entity.Order;
import com.marzetti.peluqueria.entity.OrderItem;
import com.marzetti.peluqueria.repository.OrderRepository;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create_preference/{orderNumber}")
    public ResponseEntity<PaymentPreferenceDTO> createPreference(@PathVariable String orderNumber) {
        try {
            Order order = orderRepository.findByOrderNumber(orderNumber)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            List<PreferenceItemRequest> items = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .title(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(BigDecimal.valueOf(item.getPrice()))
                        .currencyId("ARS")
                        .build();
                items.add(itemRequest);
            }

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173/checkout/success")
                    .pending("http://localhost:5173/checkout/pending")
                    .failure("http://localhost:5173/checkout/failure")
                    .build();

            com.mercadopago.client.preference.PreferencePayerRequest payer = com.mercadopago.client.preference.PreferencePayerRequest
                    .builder()
                    .email(order.getCustomerEmail())
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .payer(payer)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.ok(new PaymentPreferenceDTO(preference.getId()));

        } catch (com.mercadopago.exceptions.MPApiException e) {
            System.err.println("MP API Error: " + e.getApiResponse().getContent());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
