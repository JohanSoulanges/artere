package com.johan.artere.model;

public record HealthCheck(
        String message,
        ApplicationStatus status,
        Long countConnections
) {
}
