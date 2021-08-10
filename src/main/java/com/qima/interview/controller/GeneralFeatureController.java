package com.qima.interview.controller;

import com.qima.interview.entity.GeneralFeature;
import com.qima.interview.service.GeneralFeatureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralFeatureController {
    @Autowired
    private GeneralFeatureService generalFeatureService;

    @GetMapping("/features/{code}")
    public ResponseEntity<GeneralFeature> getByCode(@PathVariable("code") final String code) {
        return new ResponseEntity<>(generalFeatureService.getByCode(code), HttpStatus.OK);
    }
}
