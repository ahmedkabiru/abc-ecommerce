package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
