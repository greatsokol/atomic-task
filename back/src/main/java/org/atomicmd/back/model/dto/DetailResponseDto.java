package org.atomicmd.back.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DetailResponseDto {
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
}
