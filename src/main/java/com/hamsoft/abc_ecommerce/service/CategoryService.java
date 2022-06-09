package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.model.Category;

public interface CategoryService {

    Category getCategoryByName(String categoryName);

    void creatCategory(Category category);



}
