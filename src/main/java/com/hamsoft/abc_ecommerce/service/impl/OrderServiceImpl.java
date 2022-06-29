package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.dto.cart.CartDto;
import com.hamsoft.abc_ecommerce.dto.cart.CartItemDto;
import com.hamsoft.abc_ecommerce.dto.checkout.CheckoutItemDto;
import com.hamsoft.abc_ecommerce.exceptions.OrderNotFoundException;
import com.hamsoft.abc_ecommerce.model.Order;
import com.hamsoft.abc_ecommerce.model.OrderItem;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.repository.OrderItemRepository;
import com.hamsoft.abc_ecommerce.repository.OrderRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl  implements OrderService {

    final CartService cartService;
    final OrderRepository orderRepository;
    final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(CartService cartService, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;


    @Override
    public void placeOrder(User user, String sessionId) {
        CartDto cartDto = cartService.listCartItems(user);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();
        // create a  new order and save
        Order newOrder = new Order();
        newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        newOrder.setCreatedDate(new Date());
        Order savedOrder =  orderRepository.save(newOrder);
        for (CartItemDto cartItemDto : cartItemDtoList){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setCreatedDate(new Date());
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }
        cartService.deleteUserCartItems(user);
    }


    @Override
    public List<Order> getAllOrders(User user) {
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    @Override
    public Order getOrder(User user, Long id) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()){
            throw  new OrderNotFoundException("order id is not valid");
        }
        Order order = optionalOrder.get();
        if(order.getUser() != user) {
            throw  new OrderNotFoundException("order does not belong to user");
        }
        return  order;
    }

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
