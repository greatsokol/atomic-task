package org.atomicmd.back.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "master")
public class MasterEntity extends BaseEntity {
    @Column(nullable = false, name = "number", unique = true)
    @Positive(message = "Номер не может содержать отрицательное значение")
    long number;

    @Column(nullable = false, name = "description")
    @NotBlank(message = "Примечание не может быть пустым")
    @Size(max = 255, message = "Примечание не может превышать 255 символов")
    String description;

    @Column(nullable = true, name = "total")
    @PositiveOrZero(message = "Сумма не может быть отрицательной")
    BigDecimal total;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "master_id", nullable = false)
    Collection<DetailEntity> details;
}
