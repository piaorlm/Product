package com.qima.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public ResponseEntity<List<Company>> getAll(final String ids) {
        return new ResponseEntity<>(Optional.ofNullable(ids).map(idsStr -> idsStr.split(",")).map(Stream::of)
                .map(stream -> stream.map(String::trim).collect(Collectors.toSet())).map(companyService::getCompanies)
                .orElse(companyService.getAll()), HttpStatus.OK);
    }
}
