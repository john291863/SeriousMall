package com.example.seriousMall.service;

import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.dto.ProductQueryParams;
import com.example.seriousMall.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(ProductQueryParams productQueryParams);
    Product getProductInfo(Integer productId);
    ProductParams getProductParamsInfo(Product product);
    Integer createProduct(ProductParams productParams);
    Product updateProduct(ProductParams productParams, Integer productId);
    void deleteProduct(Integer productId);
    Page<Product> findProduct(PageRequest pageRequest);
}
