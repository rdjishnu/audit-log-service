package com.example.audit_log_service.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuditEventRequest(
        @NotBlank(message = "Actor cannot be blank")
        String actor,

        @NotBlank(message = "Action cannot be blank")
        String action,

        @NotBlank(message = "EntityType cannot be blank")
        String entityType,

        @NotBlank(message = "EntityId cannot be blank")
        String entityId,

        @NotNull(message = "Metadata cannot be null")
        Map<String, Object> metadata
) {}