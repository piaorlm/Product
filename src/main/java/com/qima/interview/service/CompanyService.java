package com.qima.interview.service;

import java.util.List;
import java.util.Set;

import com.qima.interview.dao.CompanyDao;
import com.qima.interview.entity.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public List<Company> getAll() {
        return companyDao.getAll();
    }

    public List<Company> getCompanies(Set<String> companyIds) {
        List<Company> companies = getAll();
        // TODO: Task 1
        return companies;
    }
}