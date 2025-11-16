package org.atomicmd.back.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class MasterPostDto {
    @NotNull
    @Positive(message = "Номер не может содержать отрицательное значение")
    private long number;
    @NotNull
    @NotBlank(message = "Примечание не может быть пустым")
    @Size(max = 255, message = "Примечание не может превышать 255 символов")
    private String description;
    Collection<DetailPostDto> details;
}
