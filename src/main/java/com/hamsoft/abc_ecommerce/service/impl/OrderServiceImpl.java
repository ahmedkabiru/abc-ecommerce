package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.dto.checkout.CheckoutItemDto;
import com.hamsoft.abc_ecommerce.service.CartService;
import com.hamsoft.abc_ecommerce.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl  implements OrderService {

    final CartService cartService;

    public OrderServiceImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;


    @Override
    public Session createStripeSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";

        //set private apiKey
        Stripe.apiKey = stripeSecretKey;

        List<SessionCreateParams.LineItem> sessionLineItem = new ArrayList<>();

        // for each product compute a session Line Item
        checkoutItemDtoList.forEach(checkoutItem-> sessionLineItem.add(createSessionLineItem(checkoutItem)));

        // build session param
        SessionCreateParams sessionCreateParams =  SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionLineItem)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(sessionCreateParams);
    }

    private   SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto){
        return  SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                //set quantity for each product
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    private  SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto){
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("USD")
                .setUnitAmount( (long)checkoutItemDto.getPrice() * 100)
                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(checkoutItemDto.getProductName())
                        .build())
                .build();
    }
}