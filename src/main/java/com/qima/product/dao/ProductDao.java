package com.qima.product.dao;

import java.util.List;

import com.qima.product.entity.Product;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import lombok.Setter;

@Repository
@Setter
@ConfigurationProperties(prefix = "product")
public class ProductDao {
    private List<Product> products;

    public List<Product> getAll() {
        return products;
    }
}
