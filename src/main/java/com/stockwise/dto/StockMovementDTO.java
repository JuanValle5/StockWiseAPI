package com.stockwise.dto;

import com.stockwise.model.StockMovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockMovementDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    private Integer quantity;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private StockMovementType type;

    @Size(max = 350, message = "La razón no puede superar los 350 caracteres")
    private String reason;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate createdAt;

    @NotNull(message = "El producto asociado es obligatorio")
    private Long productId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String productName;
}
