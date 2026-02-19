package com.stockwise.service.impl;

import com.stockwise.dto.CategoryDTO;
import com.stockwise.mapper.Mapper;
import com.stockwise.model.Category;
import com.stockwise.persistence.ICategoryDAO;
import com.stockwise.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDAO categoryDAO;

    @Autowired
    public CategoryServiceImpl(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryDAO.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Optional<CategoryDTO> findById(Long id) {
        if (id == null){
            throw new RuntimeException("Id no puede ser nulo");
        }
        Optional<Category> category = categoryDAO.findById(id);
        return category.map(Mapper::toDTO);
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {

        var prod = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
        return Mapper.toDTO(categoryDAO.save(prod));
    }

    @Override
    public void deleteById(Long id) {
        if(categoryDAO.findById(id).isEmpty()){
            throw new RuntimeException("Producto no encontrado para eliminar");
        }
        categoryDAO.deleteById(id);
    }
}
