package org.atomicmd.back.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static org.atomicmd.back.utils.Constants.DETAILS_CONSTRAINT;

@Entity
@Getter
@Setter
@Table(name = "detail", uniqueConstraints = {
        // имя детали в рамках одного мастера должно быть уникальным
        @UniqueConstraint(name = DETAILS_CONSTRAINT, columnNames = {"name", "master_id"})
})
public class DetailEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Наименование не может быть пустым")
    @Size(max = 255, message = "Наименование не может превышать 255 символов")
    private String name;

    @Column(nullable = false, name = "price")
    @PositiveOrZero(message = "Сумма не может быть отрицательной")
    private BigDecimal price;
}
