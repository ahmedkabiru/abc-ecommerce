package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category getCategoryByName(String categoryName);

    void creatCategory(Category category);

    Optional<Category> getCategoryByID(Long categoryId);

    List<Category> getAllCategories();

    void  updateCategory(Long categoryID, Category category);



}
