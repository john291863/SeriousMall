package com.example.seriousMall.service;

import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductInfo(Integer productId);
    ProductParams getProductParamsInfo(Product product);
    Integer createProduct(ProductParams productParams);
    Product updateProduct(ProductParams productParams);
}
