package com.example.audit_log_service.controller;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

   @GetMapping
    public Page<AuditEvent> getEvents(
            @RequestParam(required = false) String entityId,
            @RequestParam(required = false) String actor,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));

        if (entityId != null) {
            return auditEventRepository.findByEntityId(entityId, pageable);
        }
        if (actor != null) {
            return auditEventRepository.findByActor(actor, pageable);
        }
        if (action != null) {
            return auditEventRepository.findByAction(action, pageable);
        }
        if (startDate != null && endDate != null) {
            return auditEventRepository.findByTimestampBetween(startDate, endDate, pageable);
        }

        return auditEventRepository.findAll(pageable);
    }
}