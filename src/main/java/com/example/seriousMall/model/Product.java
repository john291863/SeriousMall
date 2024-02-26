package com.example.seriousMall.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter@Setter
@Table(name="products")
@Entity
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "category")
    private String category;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "price")
    private Integer price;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "description")
    private String description;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

}
