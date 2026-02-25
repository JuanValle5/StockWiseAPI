package com.stockwise.controller;

import com.stockwise.dto.StockMovementDTO;
import com.stockwise.service.IStockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Movimientos de stock", description = "Operaciones para entradas y salidas de inventario")
public class StockMovementController {

    private final IStockMovementService stockMovementService;

    public StockMovementController(IStockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @Operation(summary = "Listar movimientos de stock")
    @GetMapping
    public ResponseEntity<List<StockMovementDTO>> findAll() {
        return ResponseEntity.ok(stockMovementService.findAll());
    }

    @Operation(summary = "Registrar movimiento de stock")
    @PostMapping
    public ResponseEntity<StockMovementDTO> createStockMovement(@Valid @RequestBody StockMovementDTO stockMovementDTO) {
        StockMovementDTO created = stockMovementService.save(stockMovementDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }
}
