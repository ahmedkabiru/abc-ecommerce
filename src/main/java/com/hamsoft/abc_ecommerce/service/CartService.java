package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.cart.AddToCartDto;
import com.hamsoft.abc_ecommerce.dto.cart.CartDto;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.Product;
import com.hamsoft.abc_ecommerce.model.User;

public interface CartService {

    void  addToCart(AddToCartDto addToCartDto, Product product, User user);

    CartDto listCartItems(User user);

    void deleteCartItem(Long cartItemId, User user) throws CustomException;

    void  deleteUserCartItems(User user);
}
