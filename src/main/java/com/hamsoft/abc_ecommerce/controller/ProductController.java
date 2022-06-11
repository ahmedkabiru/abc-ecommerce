package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.commons.ApiResponse;
import com.hamsoft.abc_ecommerce.dto.ProductDto;
import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.service.CategoryService;
import com.hamsoft.abc_ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    final ProductService productService;
    final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse>  createProduct(@Valid @RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryService.getCategoryByID(productDto.getCategoryId());
        if(optionalCategory.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto,category);
        return new ResponseEntity<>(new ApiResponse(true, "category save successfully"), HttpStatus.CREATED);

    }

    @GetMapping
    public  ResponseEntity<Object> getAllProducts(){
        List<ProductDto> productList = productService.getAllProducts();
        return  new ResponseEntity<>(productList, HttpStatus.OK);
    }


    @PutMapping(path = "{productID}")
    public  ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productID, @Valid @RequestBody ProductDto productDto ){
        Optional<Category> optionalCategory = categoryService.getCategoryByID(productDto.getCategoryId());
        if(optionalCategory.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productID,productDto,category);
        return new ResponseEntity<>(new ApiResponse(true, "category updated successfully"), HttpStatus.OK);
    }



}
