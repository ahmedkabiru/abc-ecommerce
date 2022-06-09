package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.repository.CategoryRepository;
import com.hamsoft.abc_ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Category> getCategoryByID(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void updateCategory(Long categoryID, Category newCategory) {
       categoryRepository.findById(categoryID).ifPresent(
               category -> {
                   category.setCategoryName(newCategory.categoryName);
                   category.setDescription(newCategory.description);
                   category.setImageUrl(newCategory.imageUrl);
                   categoryRepository.save(category);
               }
       );
    }
}
