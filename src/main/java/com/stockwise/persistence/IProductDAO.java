package com.stockwise.persistence;

import com.stockwise.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductDAO{
    List<Product> findAll();
    Optional<Product> findById(Long id);
    List<Product> findProductsByLowStock();
    List<Product> findProductsByCategory(Long id);
    Product save(Product product);
    void deleteById(Long id);
}
