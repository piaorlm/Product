package com.qima.product.controller;

import java.util.List;

import com.qima.product.entity.Company;
import com.qima.product.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return new ResponseEntity<>(companyService.getAll(), HttpStatus.OK);
    }

    public ResponseEntity<Company> getById() {
        // TODO: Task 1
        // final List<Compan> companies = companyService.getCompanies();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
