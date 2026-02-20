package com.stockwise.service.impl;

import com.stockwise.dto.ProductDTO;
import com.stockwise.mapper.Mapper;
import com.stockwise.model.Category;
import com.stockwise.model.Product;
import com.stockwise.persistence.ICategoryDAO;
import com.stockwise.persistence.IProductDAO;
import com.stockwise.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductServiceImpl implements IProductService {

    private final IProductDAO productDAO;
    private final ICategoryDAO categoryDAO;

    @Autowired
    public ProductServiceImpl(IProductDAO productDAO, ICategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productDAO.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        if (id == null){
            throw new RuntimeException("Id no puede ser nulo");
        }
        Optional<Product> product = productDAO.findById(id);
        return product.map(Mapper::toDTO);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Category category = categoryDAO.findById(productDTO.getCategoryId()).orElse(null);
        if (category == null){
            throw new RuntimeException("No se encontro la categoria");
        }

        var product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .currentStock(productDTO.getCurrentStock())
                .minimunStock(productDTO.getMinimunStock())
                .createdAt(productDTO.getCreatedAt())
                .category(category)
                .build();
        return Mapper.toDTO(productDAO.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (id == null){
            throw new RuntimeException("Id no debe ser nulo");
        }
        //Verificar si existe
        Product p = productDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        //Verificar si existe la categoria
        Category c = categoryDAO.findById(id)
                        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        p.setName(productDTO.getName());
        p.setCategory(c);
        p.setPrice(productDTO.getPrice());
        p.setMinimunStock(productDTO.getMinimunStock());

        return Mapper.toDTO(productDAO.save(p));
    }

    @Override
    public List<ProductDTO> findProductsByLowStock() {
        return productDAO.findProductsByLowStock().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public List<ProductDTO> findProductsByCategory(Long id) {
        if (categoryDAO.findById(id).isEmpty()){
            throw new RuntimeException("No se encontro la categoria");
        }
        return productDAO.findProductsByCategory(id).stream().map(Mapper::toDTO).toList();
    }

    @Override
    public void deleteById(Long id) {
        if (productDAO.findById(id).isEmpty()){
            throw new RuntimeException("No se encontro el producto a borrar");
        }
        productDAO.deleteById(id);
    }
}
