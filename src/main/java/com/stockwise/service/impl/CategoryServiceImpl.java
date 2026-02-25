package com.stockwise.service.impl;

import com.stockwise.dto.CategoryDTO;
import com.stockwise.exception.BusinessException;
import com.stockwise.exception.ResourceNotFoundException;
import com.stockwise.mapper.Mapper;
import com.stockwise.model.Category;
import com.stockwise.persistence.ICategoryDAO;
import com.stockwise.service.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryDAO categoryDAO;

    public CategoryServiceImpl(ICategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryDAO.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findById(Long id) {
        if (id == null) {
            throw new BusinessException("El id no puede ser nulo");
        }
        return categoryDAO.findById(id).map(Mapper::toDTO);
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
        return Mapper.toDTO(categoryDAO.save(category));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (categoryDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Categoría no encontrada para eliminar");
        }
        categoryDAO.deleteById(id);
    }
}
