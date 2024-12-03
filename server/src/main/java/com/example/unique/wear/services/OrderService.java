package com.example.unique.wear.services;

import com.example.unique.wear.model.dto.order.OrderRequestDto;
import com.example.unique.wear.model.dto.order.OrderResponseDto;
import org.apache.coyote.BadRequestException;

import java.security.Principal;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, Principal principal) throws BadRequestException;
}
