package com.qima.product.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.qima.product.constant.ProductClassification;
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
     * Task 3, please remind candidate to use company with parent information in
     * Product entity:
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
                        && !isCompanyContained(product.getCompany(), blacklistedCompanyIds))
                .collect(Collectors.toList());
    }

    private boolean isCompanyContained(final Company company, final Set<String> companyIds) {
        if (company == null) {
            return false;
        }
        return companyIds.contains(company.getId()) || isCompanyContained(company.getParent(), companyIds);
    }

    /**
     * Task 4, mainly for logic checking:
     * <ol>
     * <li>Understand priorities are dynamic based on data</li>
     * <li>Logic about getting comparators based on priority type</li>
     * <li>Logic to sort products based on comparators one by one if result is 0
     * (equal)</li>
     * </ol>
     */
    private List<Product> sortProducts(final List<Product> products) {
        final List<String> topPrioritizedProdIds = priorityDao.getTopPrioritizedProducts();
        final List<Priority> priorities = priorityDao.getPriorities();
        // TODO: 4
        final Comparator<Product> comparator = priorities.stream().sorted(Comparator.comparingInt(Priority::getRank))
                .reduce(getTopComparator(topPrioritizedProdIds),
                        (c, priority) -> c.thenComparing(getComparator(priority)), (c1, c2) -> c1.thenComparing(c2));
        products.sort(comparator);

        return products;
    }

    private Comparator<Product> getComparator(Priority priority) {
        switch (priority.getType()) {
            case PRICE:
                return getPriceComparator(priority);
            case CLASSIFICATION:
                return getClassificationComparator(priority);
            case COMPANY:
                return getCompanyComparator(priority);
            default:
                return null;
        }
    }

    private Comparator<Product> getTopComparator(List<String> topPrioritizedProdIds) {
        final Map<String, Integer> topProdId2IndexMap = IntStream.range(0, topPrioritizedProdIds.size())
                .mapToObj(Integer::valueOf).collect(Collectors.toMap(topPrioritizedProdIds::get, Function.identity()));
        return Comparator
                .nullsLast(Comparator.comparing(Product::getId, Comparator.comparingInt(topProdId2IndexMap::get)));
    }

    private Comparator<Product> getPriceComparator(Priority priority) {
        return Comparator.nullsLast(priority.getDir().convert(Comparator.comparingDouble(Product::getPrice)));
    }

    private Comparator<Product> getClassificationComparator(Priority priority) {
        return Comparator.nullsLast(priority.getDir().convert(Comparator.comparing(Product::getClassification,
                Comparator.comparingInt(ProductClassification::getRank))));
    }

    private Comparator<Product> getCompanyComparator(Priority priority) {
        return Comparator.comparing(Product::getCompany, (c1, c2) -> {
            final boolean isC1Prioritized = isCompanyContained(c1, Set.of(priority.getSource()));
            final boolean isC2Prioritized = isCompanyContained(c2, Set.of(priority.getSource()));
            if (isC1Prioritized && !isC2Prioritized) {
                return 1;
            }

            if (!isC1Prioritized && isC2Prioritized) {
                return -1;
            }

            return 0;
        });
    }
}
