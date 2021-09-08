package com.qima.product.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.qima.product.dao.PriorityDao;
import com.qima.product.dao.ProductDao;
import com.qima.product.entity.Company;
import com.qima.product.entity.Priority;
import com.qima.product.entity.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PriorityDao priorityDao;

    @Autowired
    private CompanyService companyService;

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public List<Product> getAllWithCompanyInfo() {
        final List<Product> products = productDao.getAll();
        final Set<String> companyIdsSet = products.stream().map(Product::getCompanyId).collect(Collectors.toSet());
        final Map<String, Company> id2ComMap = companyService.getCompanies(companyIdsSet).stream()
                .collect(Collectors.toMap(Company::getId, Function.identity()));

        products.forEach(product -> product.setCompany(id2ComMap.get(product.getCompanyId())));

        return products;
    }

    public List<Product> getAllPrioritized() {
        return sortProducts(filterProducts(getAllWithCompanyInfo()));
    }

    /**
     * Task 3:
     * <ol>
     * <li>Read Product bean defination to know the company entity with parent has
     * already been there</li>
     * <li>Logic to check parent company with blacklisted companies</li>
     * <li>Better to suppose multiple layer of companies</li>
     * <li>Using stream proficiently</li>
     * </ol>
     */
    private List<Product> filterProducts(List<Product> products) {
        final Set<String> blacklistedProductIds = priorityDao.getBlackListedProducts();
        final Set<String> blacklistedCompanyIds = priorityDao.getBlackListedCompanies();
        // TODO: 3
        return products.stream()
                .filter(product -> !blacklistedProductIds.contains(product.getId())
                        && !isCompanyBlacklisted(product.getCompany(), blacklistedCompanyIds))
                .collect(Collectors.toList());
    }

    private boolean isCompanyBlacklisted(final Company company, final Set<String> blacklistedCompanyIds) {
        if (company == null) {
            return false;
        }
        return blacklistedCompanyIds.contains(company.getId())
                || isCompanyBlacklisted(company.getParent(), blacklistedCompanyIds);
    }

    private List<Product> sortProducts(final List<Product> products) {
        final List<String> topPrioritizedProdIds = priorityDao.getTopPrioritizedProducts();
        final List<Priority> priorities = priorityDao.getPriorities();
        // TODO: 4
        return products;
    }
}
