package org.atomicmd.back.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.atomicmd.back.model.entity.AuditEntity;
import org.atomicmd.back.repository.AuditRepository;
import org.atomicmd.back.service.AuditService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    @Transactional
    public void saveAuditMessage(String message) {
        if (message.length() > 255) message = message.substring(0, 255);
        auditRepository.save(new AuditEntity(message));
    }
}
