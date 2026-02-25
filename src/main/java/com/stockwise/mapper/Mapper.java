package com.stockwise.mapper;

import com.stockwise.dto.CategoryDTO;
import com.stockwise.dto.ProductDTO;
import com.stockwise.dto.StockMovementDTO;
import com.stockwise.model.Category;
import com.stockwise.model.Product;
import com.stockwise.model.StockMovement;

public class Mapper {

    private Mapper() {
    }

    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .currentStock(product.getCurrentStock())
                .minimunStock(product.getMinimunStock())
                .createdAt(product.getCreatedAt())
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }

    public static StockMovementDTO toDTO(StockMovement stockMovement) {
        if (stockMovement == null) {
            return null;
        }

        Long productId = stockMovement.getProduct() != null ? stockMovement.getProduct().getId() : null;
        String productName = stockMovement.getProduct() != null ? stockMovement.getProduct().getName() : null;

        return StockMovementDTO.builder()
                .id(stockMovement.getId())
                .quantity(stockMovement.getQuantity())
                .type(stockMovement.getType())
                .reason(stockMovement.getReason())
                .createdAt(stockMovement.getCreatedAt())
                .productId(productId)
                .productName(productName)
                .build();
    }
}
