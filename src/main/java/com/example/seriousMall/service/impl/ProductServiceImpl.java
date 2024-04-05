package com.example.seriousMall.service.impl;

import com.example.seriousMall.converter.ProductConverter;
import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.dto.ProductQueryParams;
import com.example.seriousMall.model.Product;
import com.example.seriousMall.model.ProductPhoto;
import com.example.seriousMall.repository.ProductPhotoRepository;
import com.example.seriousMall.repository.ProductRepository;
import com.example.seriousMall.service.ProductService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.seriousMall.converter.ProductConverter.convertDtoToEntity;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPhotoRepository productPhotoRepository;
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
        Product product = convertDtoToEntity(productParams, Product.class);
        if (product != null) {
            product.setCreatedDate(new Date());
            product.setLastModifiedDate(new Date());

            product = productRepository.save(product);

//            productPhoto.setProductPid(product.getProductId());
//            productPhotoRepository.save(productPhoto);
            return product.getProductId();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void createProductPhoto(MultipartFile[] files, Integer productId) throws IOException {
        String uploadedFileName = Arrays.stream(files).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        Arrays.asList(files);
        for (MultipartFile file : files) {
            ProductPhoto productPhoto = new ProductPhoto();
            byte[] bytes = file.getBytes();
            productPhoto.setPhotos(bytes);
            productPhoto.setProductPid(productId);
            productPhotoRepository.save(productPhoto);
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
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Page<Product> findProduct(PageRequest pageRequest) {
        try {
            return productRepository.findAll(pageRequest);
        } catch (PropertyReferenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
