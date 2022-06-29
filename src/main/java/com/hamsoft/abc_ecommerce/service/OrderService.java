package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.checkout.CheckoutItemDto;
import com.hamsoft.abc_ecommerce.exceptions.OrderNotFoundException;
import com.hamsoft.abc_ecommerce.model.Order;
import com.hamsoft.abc_ecommerce.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {


    Session createStripeSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException;
    void placeOrder(User user, String sessionId);

    List<Order> getAllOrders(User user);

    Order getOrder(User user, Long id) throws OrderNotFoundException;
}