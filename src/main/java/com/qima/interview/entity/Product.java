package com.qima.interview.entity;

import com.qima.interview.constant.ProductClassification;

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
