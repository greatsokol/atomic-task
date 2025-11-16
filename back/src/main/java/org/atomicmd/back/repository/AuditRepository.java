package org.atomicmd.back.repository;

import org.atomicmd.back.model.entity.AuditEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuditRepository extends CrudRepository<AuditEntity, UUID> {
}
