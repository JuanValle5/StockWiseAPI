package com.stockwise.service.impl;

import com.stockwise.dto.StockMovementDTO;
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

import java.util.List;
import java.util.Optional;

@Component
public class StockMovementServiceImpl implements IStockMovementService {

    private final IStockMovementDAO stockMovementDAO;
    private final IProductDAO productDAO;

    @Autowired
    public StockMovementServiceImpl(IStockMovementDAO stockMovementDAO, IProductDAO productDAO) {
        this.stockMovementDAO = stockMovementDAO;
        this.productDAO = productDAO;
    }

    @Override
    public List<StockMovementDTO> findAll() {
        return stockMovementDAO.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Optional<StockMovementDTO> findById(Long id) {

        if (id == null) throw new RuntimeException("El id debe ser valido ");

        Optional<StockMovement> stockMovementOptional = stockMovementDAO.findById(id);
        return stockMovementOptional.map(Mapper::toDTO);

    }

    @Override
    @Transactional
    public StockMovementDTO save(StockMovementDTO stockMovementDTO) {
        if (stockMovementDTO == null) throw new RuntimeException("El movimiento de stock es obligatorio");
        if (stockMovementDTO.getProductId() == null) throw new RuntimeException("El producto asociado es obligatorio");
        if (stockMovementDTO.getType() == null) throw new RuntimeException("El tipo de movimiento es obligatorio");
        if (stockMovementDTO.getQuantity() == null || stockMovementDTO.getQuantity() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor que cero");
        }

        Product product = productDAO.findById(stockMovementDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto asociado no encontrado"));

        long currentStock = product.getCurrentStock() == null ? 0L : product.getCurrentStock();

        if (stockMovementDTO.getType() == StockMovementType.OUT && currentStock < stockMovementDTO.getQuantity()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + currentStock);
        }

        long updatedStock = stockMovementDTO.getType() == StockMovementType.IN
                ? currentStock + stockMovementDTO.getQuantity()
                : currentStock - stockMovementDTO.getQuantity();

        product.setCurrentStock(updatedStock);
        productDAO.save(product);


        var stockMovement = StockMovement.builder()
                .quantity(stockMovementDTO.getQuantity())
                .type(stockMovementDTO.getType())
                .reason(stockMovementDTO.getReason())
                .createdAt(stockMovementDTO.getCreatedAt())
                .product(product)
                .build();



        return Mapper.toDTO(stockMovementDAO.save(stockMovement));
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new RuntimeException("Id nulo");
        stockMovementDAO.deleteById(id);
    }
}
