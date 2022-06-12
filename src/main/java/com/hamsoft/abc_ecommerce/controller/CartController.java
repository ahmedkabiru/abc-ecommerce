package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.commons.ApiResponse;
import com.hamsoft.abc_ecommerce.dto.cart.AddToCartDto;
import com.hamsoft.abc_ecommerce.dto.cart.CartDto;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.Product;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import com.hamsoft.abc_ecommerce.service.CartService;
import com.hamsoft.abc_ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    final CartService cartService;

    final ProductService productService;

    final AuthenticationService authenticationService;


    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestHeader("token") String token) throws CustomException {
        // first authenticate the token
        authenticationService.validateToken(token);
        // get the user
        User user = authenticationService.getUser(token);

        CartDto cartDto  = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@Valid @RequestBody AddToCartDto addToCartDto, @RequestHeader("token") String token) throws CustomException {
        authenticationService.validateToken(token);
        User user = authenticationService.getUser(token);
        Optional<Product> product = productService.getProductByID(addToCartDto.getProductId());
        if(product.isEmpty()) {
            throw new CustomException("Product ID " + addToCartDto.getProductId() + " does not exist");
        }
        cartService.addToCart(addToCartDto, product.get(), user);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long cartItemId,
                                                      @RequestHeader("token") String token) throws CustomException {
        authenticationService.validateToken(token);
        User user = authenticationService.getUser(token);
        cartService.deleteCartItem(cartItemId,user);
        return  new ResponseEntity<>(new ApiResponse(true,"Cart Item deleted"),HttpStatus.OK);
    }


}
