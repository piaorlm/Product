package com.qima.product.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.qima.product.entity.Company;
import com.qima.product.service.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * Task 1:
     * <ol>
     * <li>Test naming convention of RESTful</li>
     * <li>Request method defination (GET here)</li>
     * <li>How to get path variable or request param</li>
     * <li>Initializing a set and add a string</li>
     * <li>null and empty check for List</li>
     * </ol>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable final String id) {
        // TODO: Task 1
        final Company company = Optional.ofNullable(companyService.getCompanies(Set.of(id)))
                .filter(list -> !CollectionUtils.isEmpty(list)).map(list -> list.get(0)).orElse(null);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}
