package com.example.seriousMall.controller;

import com.example.seriousMall.model.Product;
import com.example.seriousMall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        for(Product pro: productList){
            System.out.println("??"+pro);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/testUnBlock")
    public String testUnBlock() {
        return "test UnBlock";
    }
}
