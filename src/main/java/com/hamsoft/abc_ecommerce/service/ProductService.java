package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.ProductDto;
import com.hamsoft.abc_ecommerce.model.Category;

import java.util.List;

public interface ProductService {


    void addProduct(ProductDto productDto, Category category);

    List<ProductDto> getAllProducts();
}
