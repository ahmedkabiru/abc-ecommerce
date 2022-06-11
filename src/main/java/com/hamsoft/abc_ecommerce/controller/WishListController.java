package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.commons.ApiResponse;
import com.hamsoft.abc_ecommerce.dto.ProductDto;
import com.hamsoft.abc_ecommerce.exceptions.CustomException;
import com.hamsoft.abc_ecommerce.model.Product;
import com.hamsoft.abc_ecommerce.model.User;
import com.hamsoft.abc_ecommerce.model.WishList;
import com.hamsoft.abc_ecommerce.service.AuthenticationService;
import com.hamsoft.abc_ecommerce.service.ProductService;
import com.hamsoft.abc_ecommerce.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishListController {

    final WishListService wishListService;

    final ProductService productService;

    final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDto productDto, @RequestHeader("token") String token) throws CustomException {
        authenticationService.validateToken(token);
        User user = authenticationService.getUser(token);
        Optional<Product> product = productService.getProductByID(productDto.getId());
        if (product.isEmpty()) {
            throw new CustomException("Product ID " + productDto.getId() + " does not exist");
        }
        WishList wishList = new WishList(user, product.get());
        wishListService.createWishList(wishList);
        return new ResponseEntity<>(new ApiResponse(true, "Added to wishlist"), HttpStatus.CREATED);
    }


    @GetMapping
    public  ResponseEntity<List<ProductDto>> getWishList(@RequestHeader("token") String token) throws CustomException {
        authenticationService.validateToken(token);
        User user = authenticationService.getUser(token);
        List<ProductDto> products = wishListService.getAllWishList(user)
                .stream().map(wishList -> new ProductDto(wishList.getProduct())).collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}
