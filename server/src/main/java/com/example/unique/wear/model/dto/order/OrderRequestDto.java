package com.example.unique.wear.model.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    private UUID userId;
    private Date orderDate;
    private UUID addressId;
    private List<OrderItemDto> orderItemRequests;
    private Double totalAmount;
    private Double discount;
    private String paymentMethod;
    private Date expectedDeliveryDate;
}
