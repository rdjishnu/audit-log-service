package com.example.audit_log_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.audit_log_service.dto.AuditEventRequest;
import com.example.audit_log_service.model.AuditEvent;
import com.example.audit_log_service.repository.AuditEventRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class AuditEventController {

    private final AuditEventRepository auditEventRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuditEvent ingestEvent(@Valid @RequestBody AuditEventRequest request) {
        AuditEvent event = AuditEvent.builder()
                .actor(request.actor())
                .action(request.action())
                .entityType(request.entityType())
                .entityId(request.entityId())
                .metadata(request.metadata())
                .build();

        return auditEventRepository.save(event);
    }
}