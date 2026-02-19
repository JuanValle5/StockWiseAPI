package com.stockwise.dto;

import com.stockwise.model.StockMovementType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovementDTO {

    private Long id;
    private Integer quantity;
    private StockMovementType type;
    private String reason;
    private LocalDate createdAt;
    private Long productId;
    private String productName;
}
