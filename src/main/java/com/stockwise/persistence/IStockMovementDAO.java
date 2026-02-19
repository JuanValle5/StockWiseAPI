package com.stockwise.persistence;

import com.stockwise.model.StockMovement;

import java.util.List;
import java.util.Optional;

public interface IStockMovementDAO {
    List<StockMovement> findAll();
    Optional<StockMovement> findById(Long id);
    StockMovement save(StockMovement stockMovement);
    void deleteById(Long id);
}
