package com.stockwise.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long currentStock;
    private Long minimunStock;
    private LocalDate createdAt;
    private Long categoryId;
    private String categoryName;
}
