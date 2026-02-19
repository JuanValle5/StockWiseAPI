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
    public StockMovementDTO save(StockMovementDTO stockMovementDTO) {
        Product productOptional = productDAO.findById(stockMovementDTO.getProductId()).orElse(null);
        boolean type = false;
        if (productOptional == null) throw new RuntimeException("Producto asociado no encontrado");
        if (stockMovementDTO.getType() == StockMovementType.OUT &&
                productOptional.getCurrentStock() < stockMovementDTO.getQuantity()){
            throw new RuntimeException("Stock insuficiente. Disponible: " + productOptional.getCurrentStock());
        }
        if(stockMovementDTO.getType() == StockMovementType.IN){
            type = true;
        }

        //Pendiente la implementacion a mejorar de este codigo
        productOptional.setCurrentStock(
                type ?
                        productOptional.getCurrentStock() + stockMovementDTO.getQuantity():
                        productOptional.getCurrentStock() - stockMovementDTO.getQuantity());


        var stockMovement = StockMovement.builder()
                .quantity(stockMovementDTO.getQuantity())
                .type(stockMovementDTO.getType())
                .reason(stockMovementDTO.getReason())
                .createdAt(stockMovementDTO.getCreatedAt())
                .product(productOptional)
                .build();



        return Mapper.toDTO(stockMovementDAO.save(stockMovement));
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new RuntimeException("Id nulo");
        stockMovementDAO.deleteById(id);
    }
}
