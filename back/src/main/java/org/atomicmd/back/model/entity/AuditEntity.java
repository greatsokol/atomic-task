package org.atomicmd.back.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "audit")
public class AuditEntity extends BaseEntity {
    @Column(name = "message", nullable = false)
    @NotBlank(message = "Наименование не может быть пустым")
    @Size(max = 255, message = "Наименование не может превышать 255 символов")
    private String message;

    public AuditEntity(String message) {
        setId(java.util.UUID.randomUUID());
        this.message = message;
    }
}
