package com.stockwise.service;

import com.stockwise.dto.ProductDTO;
import com.stockwise.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<ProductDTO> findAll();
    Optional<ProductDTO> findById(Long id);
    ProductDTO save(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    List<ProductDTO> findProductsByLowStock();
    List<ProductDTO> findProductsByCategory(Long id);
    void deleteById(Long id);
}
