package com.example.seriousMall.model;

import com.example.seriousMall.constant.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter@Setter
@Entity
@Table(name = "product_photo")
public class ProductPhoto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_photo_id")
    private Integer productPhotoId;
    @Column(name="product_pid")
    private Integer productPid;
    @Column(name="photo")
    private byte[] photos;


}
