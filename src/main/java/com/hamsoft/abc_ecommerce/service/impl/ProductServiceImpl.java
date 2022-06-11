package com.hamsoft.abc_ecommerce.service.impl;

import com.hamsoft.abc_ecommerce.dto.ProductDto;
import com.hamsoft.abc_ecommerce.model.Category;
import com.hamsoft.abc_ecommerce.model.Product;
import com.hamsoft.abc_ecommerce.repository.ProductRepository;
import com.hamsoft.abc_ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto,category);
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::new).collect(Collectors.toList());
    }

    @Override
    public void updateProduct(Long productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto,category);
        product.setId(productID);
        productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductByID(Long productId) {
        return productRepository.findById(productId);
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return product;
    }




}
