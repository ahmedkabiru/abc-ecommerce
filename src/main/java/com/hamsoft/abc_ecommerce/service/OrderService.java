package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.checkout.CheckoutItemDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.List;

public interface OrderService {


    Session createStripeSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException;
}