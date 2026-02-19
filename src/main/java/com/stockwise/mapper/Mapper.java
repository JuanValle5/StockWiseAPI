package com.stockwise.mapper;

import com.stockwise.dto.CategoryDTO;
import com.stockwise.dto.ProductDTO;
import com.stockwise.dto.StockMovementDTO;
import com.stockwise.model.Category;
import com.stockwise.model.Product;
import com.stockwise.model.StockMovement;

public class Mapper {

    //Mapeo de category a categoryDTO
    public static CategoryDTO toDTO(Category c){
        if(c == null) return null;
        return CategoryDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .description(c.getDescription())
                .build();
    }
    //Mapeo de product a productDTO
    public static ProductDTO toDTO(Product p){
        if(p == null) return null;
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .currentStock(p.getCurrentStock())
                .minimunStock(p.getMinimunStock())
                .createdAt(p.getCreatedAt())
                .categoryId(p.getCategory().getId())
                .categoryName(p.getCategory().getName())
                .build();
    }
    //Mapeo de sotckmovement a sotckmovementDTO
    public static StockMovementDTO toDTO(StockMovement s){
        if(s == null) return null;
        return StockMovementDTO.builder()
                .id(s.getId())
                .quantity(s.getQuantity())
                .type(s.getType())
                .reason(s.getReason())
                .createdAt(s.getCreatedAt())
                .productId(s.getProduct().getId())
                .productName(s.getProduct().getName())
                .build();
    }
}
