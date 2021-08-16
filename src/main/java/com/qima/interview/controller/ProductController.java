package com.qima.interview.controller;

import java.util.List;

import com.qima.interview.entity.Product;
import com.qima.interview.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/full-info")
    public ResponseEntity<List<Product>> getAllWithCompany() {
        return new ResponseEntity<>(productService.getAllWithCompanyInfo(), HttpStatus.OK);
    }

    @GetMapping("/prioritization")
    public ResponseEntity<List<Product>> getPrioritizedProducts() {
        return new ResponseEntity<>(productService.getAllPrioritized(), HttpStatus.OK);
    }
}
