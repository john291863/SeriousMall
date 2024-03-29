package com.example.seriousMall.service.impl;

import com.example.seriousMall.converter.ProductConverter;
import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.dto.ProductQueryParams;
import com.example.seriousMall.model.Product;
import com.example.seriousMall.repository.ProductRepository;
import com.example.seriousMall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductConverter productConverter;

    @Override
    public List<Product> getAllProducts(ProductQueryParams productQueryParams) {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
//        return productRepository.findAll();
    }

    @Override
    public Product getProductInfo(Integer productId) {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return product;
        }
    }

    @Override
    public ProductParams getProductParamsInfo(Product product) {
        //to do
        return null;
    }

    @Override
    public Integer createProduct(ProductParams productParams) {
        Product product = productConverter.convertDtoToEntity(productParams);

        if (product != null) {
            product.setCreatedDate(new Date());
            product.setLastModifiedDate(new Date());

            product = productRepository.save(product);
            return product.getProductId();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Product updateProduct(ProductParams productParams, Integer productId) {
        Product product = productRepository.findByProductId(productId);

        if (product != null) {
            product.setProductName(productParams.getProductName());
            product.setCategory(productParams.getCategory());
            product.setStock(productParams.getStock());
            product.setImageUrl(productParams.getImageUrl());
            product.setDescription(productParams.getDescription());
            product.setPrice(productParams.getPrice());

            product.setCreatedDate(product.getCreatedDate());
            product.setLastModifiedDate(new Date());
            return productRepository.save(product);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> findProduct(PageRequest pageRequest){
        try {
            return productRepository.findAll(pageRequest);
        }
        catch (PropertyReferenceException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
