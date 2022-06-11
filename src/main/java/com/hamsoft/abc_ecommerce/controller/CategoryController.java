package com.hamsoft.abc_ecommerce.controller;

import com.hamsoft.abc_ecommerce.commons.ApiResponse;
import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @GetMapping
    public  ResponseEntity<List<Category>> getAllCategories(){
      List<Category> categories =  categoryService.getAllCategories();
      return  new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PutMapping(path = "{categoryID}")
    public  ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryID, @Valid @RequestBody Category category){

        Optional<Category> isCategoryExist = categoryService.getCategoryByID(categoryID);
        if(isCategoryExist.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
        }
        categoryService.updateCategory(categoryID, category);
        return new ResponseEntity<>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
    }


}
