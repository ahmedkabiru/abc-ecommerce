package com.hamsoft.abc_ecommerce.service;

import com.hamsoft.abc_ecommerce.dto.product.ProductDto;
import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {


    void addProduct(ProductDto productDto, Category category);

    List<ProductDto> getAllProducts();

    void  updateProduct(Long productID,ProductDto productDto, Category category);

    Optional<Product> getProductByID(Long productId);
}
