package com.example.seriousMall.controller;

import com.example.seriousMall.constant.ProductCategory;
import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.dto.ProductQueryParams;
import com.example.seriousMall.model.Employee;
import com.example.seriousMall.model.Product;
import com.example.seriousMall.repository.EmployeeRepository;
import com.example.seriousMall.repository.ProductRepository;
import com.example.seriousMall.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    public ResponseEntity<List<Product>> getAllProducts(
            //get請求，字串位置沒有要求

            //查詢條件 filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序 sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset

    )
    {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setProductCategory(category);
        productQueryParams.setSort(sort);
        productQueryParams.setSearch(search);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        productQueryParams.setOrderBy(orderBy);

        List<Product> productList = productService.getAllProducts(productQueryParams);

        for (Product pro : productList) {
            System.out.println("??" + pro);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductInfo(@PathVariable Integer productId) {
        Product product = productService.getProductInfo(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductParams productParams) {
        Integer productId = productService.createProduct(productParams);
        Product product = productService.getProductInfo(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductParams productParams,
                                                 @PathVariable Integer productId) {
        Product product = productService.updateProduct(productParams, productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
    }

}

