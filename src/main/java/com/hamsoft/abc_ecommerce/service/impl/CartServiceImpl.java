package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.dto.cart.AddToCartDto;
import com.hamsoft.abc_ecommerce.dto.cart.CartDto;
import com.hamsoft.abc_ecommerce.dto.cart.CartItemDto;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.Cart;
import com.hamsoft.abc_ecommerce.model.Product;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.repository.CartRepository;
import com.hamsoft.abc_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    final CartRepository cartRepository;


    @Override
    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }

    @Override
    public CartDto listCartItems(User user) {
        List<Cart> carts =  cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartDtos = carts.stream().map(CartItemDto::new).toList();
        double totalSum =  carts.stream().map( cart -> cart.getProduct().getPrice()).reduce(0.0, Double::sum);
        return new CartDto(cartDtos,totalSum);
    }

    @Override
    public void deleteCartItem(Long cartItemId, User user) throws CustomException {
        Optional<Cart> cart = cartRepository.findById(cartItemId);
        if(cart.isEmpty()){
            throw new CustomException("cartItemId not valid");
        }
        cartRepository.deleteById(cart.get().getId());
    }

    @Override
    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
