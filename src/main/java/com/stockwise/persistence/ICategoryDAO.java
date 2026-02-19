package com.stockwise.persistence;

import com.stockwise.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryDAO {

    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
}
