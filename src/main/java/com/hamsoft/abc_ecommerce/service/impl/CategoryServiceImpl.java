package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.repository.CategoryRepository;
import com.hamsoft.abc_ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public void creatCategory(Category category) {
        categoryRepository.save(category);
    }
}
