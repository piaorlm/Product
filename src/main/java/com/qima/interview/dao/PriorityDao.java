package com.qima.interview.dao;

import java.util.List;
import java.util.Set;

import com.qima.interview.entity.Priority;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Repository
@Setter
@Getter
@ConfigurationProperties(prefix = "priority")
public class PriorityDao {
    private List<Priority> priorities;
    private List<String> topPrioritizedProducts;
    private Set<String> blackListedProducts;
    private Set<String> blackListedCompanies;
}
