package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.commons.ApiResponse;
import com.hamsoft.abc_ecommerce.dto.checkout.CheckoutItemDto;
import com.hamsoft.abc_ecommerce.dto.checkout.StripeResponse;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.Order;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import com.hamsoft.abc_ecommerce.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    final OrderService orderService;
    final AuthenticationService authenticationService;

    public OrderController(OrderService orderService, AuthenticationService authenticationService) {
        this.orderService = orderService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public  ResponseEntity<List<Order>> getAllOrders(@RequestHeader String token) throws CustomException {
        // validate token
        authenticationService.validateToken(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        List<Order> orders = orderService.getAllOrders(user);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public  ResponseEntity<Order> getOrderById(@PathVariable("id") Long id,@RequestHeader String token ) throws CustomException {
        // validate token
        authenticationService.validateToken(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        Order order = orderService.getOrder(user, id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PostMapping("/add")
    public  ResponseEntity<ApiResponse>  placeOrder(@RequestParam String sessionId, @RequestHeader String token) throws CustomException {
        // validate token
        authenticationService.validateToken(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        orderService.placeOrder(user, sessionId);

        return  new ResponseEntity<>(new ApiResponse(true,"Order placed"), HttpStatus.OK);
    }
    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        Session session = orderService.createStripeSession(checkoutItemDtoList);

        return  new ResponseEntity<>(new StripeResponse(session.getId()), HttpStatus.OK);
    }
}
