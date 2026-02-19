package com.stockwise.service;

import com.stockwise.dto.StockMovementDTO;
import com.stockwise.model.StockMovement;

import java.util.List;
import java.util.Optional;

public interface IStockMovementService {
    List<StockMovementDTO> findAll();
    Optional<StockMovementDTO> findById(Long id);
    StockMovementDTO save(StockMovementDTO stockMovementDTO);
    void deleteById(Long id);
}
