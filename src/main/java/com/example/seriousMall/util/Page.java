package com.example.seriousMall.util;

import com.example.seriousMall.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer total;
    private Integer limit;
    private Integer offset;
    private List<Product> result;
}
