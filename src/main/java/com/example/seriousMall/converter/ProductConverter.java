package com.example.seriousMall.converter;

import com.example.seriousMall.dto.ProductParams;
import com.example.seriousMall.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProductConverter {
    /*
    * 這支程式是用來轉換的，dto => vo 相反亦同
    * */

    public ProductParams converterEntityToDto(Product product){
        return new ModelMapper().map(product, ProductParams.class);
    }
    public Product convertDtoToEntity (ProductParams productParams){
        return new ModelMapper().map(productParams, Product.class);
    }
}
