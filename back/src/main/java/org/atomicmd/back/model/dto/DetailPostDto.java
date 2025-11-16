package org.atomicmd.back.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DetailPostDto {
    @NotNull
    @NotBlank(message = "Примечание не может быть пустым")
    @Size(max = 255, message = "Примечание не может превышать 255 символов")
    private String name;
    @NotNull
    @Positive(message = "Сумма не может быть отрицательной")
    private BigDecimal price;
}
