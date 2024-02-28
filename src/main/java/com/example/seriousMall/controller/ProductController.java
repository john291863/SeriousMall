package com.example.seriousMall.controller;

import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.model.Employee;
import com.example.seriousMall.model.Product;
import com.example.seriousMall.repository.EmployeeRepository;
import com.example.seriousMall.repository.ProductRepository;
import com.example.seriousMall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/testUnBlock")
    public String testUnBlock() {
        return "test UnBlock";
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        for(Product pro: productList){
            System.out.println("??"+pro);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductInfo(@PathVariable Integer productId){
        Product product = productService.getProductInfo(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductParams productParams){
        Integer productId = productService.createProduct(productParams);
        Product product = productService.getProductInfo(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody@Valid ProductParams productParams,
                                                 @PathVariable Integer productId){
        Product product = productService.getProductInfo(productId);

        if(product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productParams));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}

