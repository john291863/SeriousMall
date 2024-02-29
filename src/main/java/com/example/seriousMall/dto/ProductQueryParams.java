package com.example.seriousMall.dto;

import com.example.seriousMall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryParams {
    private String search;
    private ProductCategory productCategory;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
