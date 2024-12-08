package com.example.unique.wear.auth.util;

import com.example.unique.wear.model.entity.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentIntentService {

    public Map<String, String> createPaymentIntent(Order order) throws StripeException {
        String id = order.getUser().getId().toString();
        Map<String, String> metaData = new HashMap<>();
        metaData.put("orderId", id);
        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount(10L)
                .setCurrency("USD")
                .putAllMetadata(metaData)
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
        Map<String, String> data = new HashMap<>();
        data.put("client_secret", paymentIntent.getClientSecret());
        return data;
    }
}
