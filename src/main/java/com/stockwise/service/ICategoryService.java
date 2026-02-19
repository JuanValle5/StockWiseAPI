package com.stockwise.service;

import com.stockwise.dto.CategoryDTO;
import com.stockwise.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoryDTO> findAll();
    Optional<CategoryDTO> findById(Long id);
    CategoryDTO save(CategoryDTO categoryDTO);
    void deleteById(Long id);
}
