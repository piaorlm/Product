package com.qima.interview.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import com.qima.interview.entity.Company;
import com.qima.interview.entity.Relation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Repository
@Setter
@Getter
@ConfigurationProperties(prefix = "company")
public class CompanyDao {
    private List<Company> companies;

    private List<Relation> relations;

    @PostConstruct
    private void initCompanies() {
        // TODO: Task 2
    }

    public List<Company> getAll() {
        return companies;
    }
}
