package com.qima.product.dao;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.qima.product.entity.Company;
import com.qima.product.entity.Relation;

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

    /**
     * Task 2 (could ignore empty check here to make it simple and quick):
     * <ol>
     * <li>Logic to implement this (worst to use three layers of loops)</li>
     * <li>Construct a map from list</li>
     * <li>Using stream proficiently</li>
     * </ol>
     */
    @PostConstruct
    private void initCompanies() {
        // TODO: Task 2
        final Map<String, Company> companyId2Entity = companies.stream()
                .collect(Collectors.toMap(Company::getId, Function.identity()));
        relations.forEach((relation) -> {
            final Company subordinate = companyId2Entity.get(relation.getSubordinateId());
            final Company parent = companyId2Entity.get(relation.getParentId());
            subordinate.setParent(parent);
        });
    }

    public List<Company> getAll() {
        return companies;
    }
}
