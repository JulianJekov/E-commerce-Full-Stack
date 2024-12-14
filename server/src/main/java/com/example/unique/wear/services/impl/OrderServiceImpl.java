package com.example.unique.wear.services.impl;

import com.example.unique.wear.auth.model.entity.User;
import com.example.unique.wear.auth.util.PaymentIntentService;
import com.example.unique.wear.model.dto.order.OrderRequestDto;
import com.example.unique.wear.model.dto.order.OrderResponseDto;
import com.example.unique.wear.model.entity.*;
import com.example.unique.wear.model.enums.OrderStatus;
import com.example.unique.wear.model.enums.PaymentStatus;
import com.example.unique.wear.repositories.OrderRepository;
import com.example.unique.wear.services.OrderService;
import com.example.unique.wear.services.ProductService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final UserDetailsService userDetailsService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final PaymentIntentService paymentIntentService;

    public OrderServiceImpl(UserDetailsService userDetailsService,
                            ProductService productService,
                            OrderRepository orderRepository, PaymentIntentService paymentIntentService) {
        this.userDetailsService = userDetailsService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.paymentIntentService = paymentIntentService;
    }

    @Transactional
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto,
                                        Principal principal) throws BadRequestException, StripeException {
        //TODO: validate dtos
        //TODO: make a user service to get a user
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        Address address = getAddressFromUser(orderRequestDto, user);

        Order order = buildOrder(orderRequestDto, user, address);
        List<OrderItem> orderItems = getOrderItems(orderRequestDto, order);
        order.setOrderItems(orderItems);

        Payment payment = createPayment(order);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .orderId(savedOrder.getId())
                .build();

        if (Objects.equals(orderRequestDto.getPaymentMethod(), "CARD")) {
            orderResponseDto.setCredentials(paymentIntentService.createPaymentIntent(order));
        }
        return orderResponseDto;
    }

    @Override
    public Map<String, String> updateStatus(String paymentIntentId, String status) {
        try{
            PaymentIntent paymentIntent= PaymentIntent.retrieve(paymentIntentId);
            if (paymentIntent != null && paymentIntent.getStatus().equals("succeeded")) {
                String orderId = paymentIntent.getMetadata().get("orderId") ;

                Order order= orderRepository.findById(UUID.fromString(orderId))
                        .orElseThrow(BadRequestException::new);
                Payment payment = getPayment(order, paymentIntent);

                setOrder(order, paymentIntent, payment);
                Order savedOrder = orderRepository.save(order);

                Map<String,String> map = new HashMap<>();
                map.put("orderId", String.valueOf(savedOrder.getId()));
                return map;
            }
            else{
                throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
        }
    }

    private static void setOrder(Order order, PaymentIntent paymentIntent, Payment payment) {
        order.setPaymentMethod(paymentIntent.getPaymentMethod());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        order.setPayment(payment);
    }

    private static Payment getPayment(Order order, PaymentIntent paymentIntent) {
        Payment payment = order.getPayment();
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setPaymentMethod(paymentIntent.getPaymentMethod());
        return payment;
    }

    private static Payment createPayment(Order order) {
        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(new Date());
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        //TODO: use enum for payment method
        payment.setPaymentMethod(order.getPaymentMethod());
        return payment;
    }

    private List<OrderItem> getOrderItems(OrderRequestDto orderRequestDto, Order order) {
        return orderRequestDto.getOrderItemRequests().stream().map(orderItemRequest -> {
            try {
                Product product = productService.fetchProductById(orderItemRequest.getProductId());
                return OrderItem.builder()
                        .product(product)
                        .productVariantId(orderItemRequest.getProductVariantId())
                        .quantity(orderItemRequest.getQuantity())
                        .order(order)
                        .build();
            } catch (Exception e) {
                //TODO: make it custom exception to provide more meaningful feedback
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private static Order buildOrder(OrderRequestDto orderRequestDto, User user, Address address) {
        return Order.builder()
                .user(user)
                .address(address)
                .totalAmount(orderRequestDto.getTotalAmount())
                .orderDate(orderRequestDto.getOrderDate())
                .discount(orderRequestDto.getDiscount())
                .expectedDeliveryDate(orderRequestDto.getExpectedDeliveryDate())
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    private static Address getAddressFromUser(OrderRequestDto orderRequestDto, User user) throws BadRequestException {
        return user
                .getAddresses()
                .stream()
                .filter(address1 -> orderRequestDto.getAddressId().equals(address1.getId()))
                .findFirst()
                .orElseThrow(BadRequestException::new);
    }
}
