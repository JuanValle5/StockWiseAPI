package com.stockwise.repository;

import com.stockwise.model.StockMovement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends CrudRepository<StockMovement,Long> {
}
