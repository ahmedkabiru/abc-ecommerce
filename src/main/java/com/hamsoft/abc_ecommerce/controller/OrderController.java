package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.dto.checkout.CheckoutItemDto;
import com.hamsoft.abc_ecommerce.dto.checkout.StripeResponse;
import com.hamsoft.abc_ecommerce.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        Session session = orderService.createStripeSession(checkoutItemDtoList);

        return  new ResponseEntity<>(new StripeResponse(session.getId()), HttpStatus.OK);
    }
}
