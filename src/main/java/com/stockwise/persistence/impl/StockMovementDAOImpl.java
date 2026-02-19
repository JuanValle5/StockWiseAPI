package com.stockwise.persistence.impl;

import com.stockwise.model.StockMovement;
import com.stockwise.persistence.IStockMovementDAO;
import com.stockwise.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StockMovementDAOImpl implements IStockMovementDAO {

    private final StockMovementRepository stockMovementRepository;

    @Autowired
    public StockMovementDAOImpl(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }


    @Override
    public List<StockMovement> findAll() {
        return (List<StockMovement>) stockMovementRepository.findAll();
    }

    @Override
    public Optional<StockMovement> findById(Long id) {
        return stockMovementRepository.findById(id);
    }

    @Override
    public StockMovement save(StockMovement stockMovement) {
        return stockMovementRepository.save(stockMovement);
    }

    @Override
    public void deleteById(Long id) {
        stockMovementRepository.deleteById(id);
    }
}
