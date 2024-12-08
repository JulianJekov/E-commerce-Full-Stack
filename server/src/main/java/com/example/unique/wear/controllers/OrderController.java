package com.example.unique.wear.controllers;

import com.example.unique.wear.model.dto.order.OrderRequestDto;
import com.example.unique.wear.model.dto.order.OrderResponseDto;
import com.example.unique.wear.services.OrderService;
import com.stripe.exception.StripeException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                                        Principal principal) throws BadRequestException, StripeException {
        OrderResponseDto order = orderService.createOrder(orderRequestDto, principal);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/update-payment")
    public ResponseEntity<?> updatePaymentStatus(@RequestBody Map<String, String> request) {
        Map<String, String> response = orderService.updateStatus(request.get("paymentIntent"), request.get("status"));
        return ResponseEntity.ok(response);
    }

}
