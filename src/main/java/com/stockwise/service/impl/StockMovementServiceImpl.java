package com.stockwise.service.impl;

import com.stockwise.dto.StockMovementDTO;
import com.stockwise.exception.BusinessException;
import com.stockwise.exception.ResourceNotFoundException;
import com.stockwise.mapper.Mapper;
import com.stockwise.model.Product;
import com.stockwise.model.StockMovement;
import com.stockwise.model.StockMovementType;
import com.stockwise.persistence.IProductDAO;
import com.stockwise.persistence.IStockMovementDAO;
import com.stockwise.service.IStockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockMovementServiceImpl implements IStockMovementService {

    private final IStockMovementDAO stockMovementDAO;
    private final IProductDAO productDAO;

    public StockMovementServiceImpl(IStockMovementDAO stockMovementDAO, IProductDAO productDAO) {
        this.stockMovementDAO = stockMovementDAO;
        this.productDAO = productDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementDTO> findAll() {
        return stockMovementDAO.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockMovementDTO> findById(Long id) {
        if (id == null) {
            throw new BusinessException("El id debe ser válido");
        }

        return stockMovementDAO.findById(id).map(Mapper::toDTO);
    }

    @Override
    @Transactional
    public StockMovementDTO save(StockMovementDTO stockMovementDTO) {
        Product product = productDAO.findById(stockMovementDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto asociado no encontrado"));

        if (stockMovementDTO.getType() == StockMovementType.OUT &&
                product.getCurrentStock() < stockMovementDTO.getQuantity()) {
            throw new BusinessException("Stock insuficiente. Disponible: " + product.getCurrentStock());
        }

        long newStock = stockMovementDTO.getType() == StockMovementType.IN
                ? product.getCurrentStock() + stockMovementDTO.getQuantity()
                : product.getCurrentStock() - stockMovementDTO.getQuantity();

        product.setCurrentStock(newStock);
        productDAO.save(product);

        StockMovement stockMovement = StockMovement.builder()
                .quantity(stockMovementDTO.getQuantity())
                .type(stockMovementDTO.getType())
                .reason(stockMovementDTO.getReason())
                .createdAt(stockMovementDTO.getCreatedAt())
                .product(product)
                .build();

        return Mapper.toDTO(stockMovementDAO.save(stockMovement));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            throw new BusinessException("Id nulo");
        }
        stockMovementDAO.deleteById(id);
    }
}
