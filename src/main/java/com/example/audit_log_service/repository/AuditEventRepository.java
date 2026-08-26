package com.example.audit_log_service.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.audit_log_service.model.AuditEvent;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
    
    // We removed "OrderByTimestampDesc" from the name because Pageable handles sorting for us!
    Page<AuditEvent> findByEntityId(String entityId, Pageable pageable);
    Page<AuditEvent> findByActor(String actor, Pageable pageable);
}