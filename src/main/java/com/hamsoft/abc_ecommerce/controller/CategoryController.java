package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.config.ApiResponse;
import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        if(Objects.nonNull(categoryService.getCategoryByName(category.categoryName))){
           return  new ResponseEntity<>(new ApiResponse(false,"Category Already Exist"), HttpStatus.CONFLICT);
        }
        categoryService.creatCategory(category);
        return  new ResponseEntity<>(new ApiResponse(true,"Category Created"), HttpStatus.CREATED);
    }


}
