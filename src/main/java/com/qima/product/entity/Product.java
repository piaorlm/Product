package com.qima.product.entity;

import com.qima.product.constant.ProductClassification;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private String companyId;
    private Double price;
    private ProductClassification classification;
    private Company company;
}
