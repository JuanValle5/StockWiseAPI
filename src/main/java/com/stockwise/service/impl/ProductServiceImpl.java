package com.stockwise.service.impl;

import com.stockwise.dto.ProductDTO;
import com.stockwise.exception.BusinessException;
import com.stockwise.exception.ResourceNotFoundException;
import com.stockwise.mapper.Mapper;
import com.stockwise.model.Category;
import com.stockwise.model.Product;
import com.stockwise.persistence.ICategoryDAO;
import com.stockwise.persistence.IProductDAO;
import com.stockwise.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductDAO productDAO;
    private final ICategoryDAO categoryDAO;

    public ProductServiceImpl(IProductDAO productDAO, ICategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productDAO.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findById(Long id) {
        if (id == null) {
            throw new BusinessException("El id del producto no puede ser nulo");
        }
        return productDAO.findById(id).map(Mapper::toDTO);
    }

    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        Category category = categoryDAO.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la categoría asociada"));

        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .currentStock(productDTO.getCurrentStock())
                .minimunStock(productDTO.getMinimunStock())
                .createdAt(LocalDate.now())
                .category(category)
                .build();

        return Mapper.toDTO(productDAO.save(product));
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (id == null) {
            throw new BusinessException("El id del producto no puede ser nulo");
        }

        Product product = productDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        Category category = categoryDAO.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCurrentStock(productDTO.getCurrentStock());
        product.setMinimunStock(productDTO.getMinimunStock());
        product.setCategory(category);

        return Mapper.toDTO(productDAO.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findProductsByLowStock() {
        return productDAO.findProductsByLowStock().stream().map(Mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findProductsByCategory(Long id) {
        if (categoryDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("No se encontró la categoría");
        }
        return productDAO.findProductsByCategory(id).stream().map(Mapper::toDTO).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (productDAO.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el producto a eliminar");
        }
        productDAO.deleteById(id);
    }
}
