package com.stockwise.service.impl;

import com.stockwise.dto.StockMovementDTO;
import com.stockwise.model.Product;
import com.stockwise.model.StockMovement;
import com.stockwise.model.StockMovementType;
import com.stockwise.persistence.IProductDAO;
import com.stockwise.persistence.IStockMovementDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockMovementServiceImplTest {

    @Mock
    private IStockMovementDAO stockMovementDAO;

    @Mock
    private IProductDAO productDAO;

    @InjectMocks
    private StockMovementServiceImpl stockMovementService;

    @Test
    void saveShouldPersistUpdatedStockAndMovementForInOperation() {
        Product product = Product.builder().id(1L).name("Laptop").currentStock(10L).build();
        StockMovementDTO movementDTO = StockMovementDTO.builder()
                .productId(1L)
                .quantity(5)
                .type(StockMovementType.IN)
                .reason("Compra")
                .createdAt(LocalDate.now())
                .build();

        StockMovement savedMovement = StockMovement.builder()
                .id(99L)
                .quantity(5)
                .type(StockMovementType.IN)
                .reason("Compra")
                .createdAt(movementDTO.getCreatedAt())
                .product(product)
                .build();

        when(productDAO.findById(1L)).thenReturn(Optional.of(product));
        when(productDAO.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockMovementDAO.save(any(StockMovement.class))).thenReturn(savedMovement);

        StockMovementDTO result = stockMovementService.save(movementDTO);

        assertEquals(15L, product.getCurrentStock());
        assertEquals(99L, result.getId());
        verify(productDAO).save(product);
        verify(stockMovementDAO).save(any(StockMovement.class));
    }

    @Test
    void saveShouldRejectOutOperationWhenStockIsInsufficient() {
        Product product = Product.builder().id(1L).name("Laptop").currentStock(2L).build();
        StockMovementDTO movementDTO = StockMovementDTO.builder()
                .productId(1L)
                .quantity(5)
                .type(StockMovementType.OUT)
                .reason("Venta")
                .build();

        when(productDAO.findById(1L)).thenReturn(Optional.of(product));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> stockMovementService.save(movementDTO));

        assertEquals("Stock insuficiente. Disponible: 2", exception.getMessage());
        verify(productDAO, never()).save(any(Product.class));
        verify(stockMovementDAO, never()).save(any(StockMovement.class));
    }
}
