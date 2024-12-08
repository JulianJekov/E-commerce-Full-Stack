package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.order.OrderRequestDto;
import com.example.unique.wear.model.dto.order.OrderResponseDto;
import com.stripe.exception.StripeException;
import org.apache.coyote.BadRequestException;

import java.security.Principal;
import java.util.Map;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Principal principal) throws BadRequestException, StripeException;

    Map<String, String> updateStatus(String paymentIntent, String status);
}
