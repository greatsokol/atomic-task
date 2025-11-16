package org.atomicmd.back.repository;

import org.atomicmd.back.model.entity.MasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MasterRepository extends JpaRepository<MasterEntity, UUID> {
}
