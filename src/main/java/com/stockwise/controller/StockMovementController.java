package com.stockwise.controller;

import com.stockwise.dto.StockMovementDTO;
import com.stockwise.service.IStockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/stockMovement")
public class StockMovementController {

    private final IStockMovementService stockMovementService;

    @Autowired
    public StockMovementController(IStockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(stockMovementService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createdStockMovement(@RequestBody StockMovementDTO stockMovementDTO){
        StockMovementDTO stockMovementDTO1 = stockMovementService.save(stockMovementDTO);
        return ResponseEntity
                .created(URI.create("/api/product/create" + stockMovementDTO1.getId()))
                .body(stockMovementDTO1);
    }
}
