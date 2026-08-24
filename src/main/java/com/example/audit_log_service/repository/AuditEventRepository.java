package com.example.audit_log_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.audit_log_service.model.AuditEvent;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
}