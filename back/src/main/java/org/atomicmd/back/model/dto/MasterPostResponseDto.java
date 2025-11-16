package org.atomicmd.back.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MasterPostResponseDto {
    @NotNull
    private UUID id;
    @NotNull
    @Positive
    private long number;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal total;
    Collection<DetailResponseDto> details;
}
