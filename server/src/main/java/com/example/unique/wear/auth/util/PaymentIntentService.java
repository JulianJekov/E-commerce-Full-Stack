package com.example.unique.wear.auth.util;

import com.example.unique.wear.model.entity.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentIntentService {

    public Map<String, String> createPaymentIntent(Order order) throws StripeException {
        Map<String, String> metaData = new HashMap<>();
        metaData.put("orderId", order.getId().toString());
        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount(100L)
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
