package com.johan.artere.service;

import com.johan.artere.model.ApplicationStatus;
import com.johan.artere.model.HealthCheck;
import com.johan.artere.repository.HealthCheckRepository;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    final HealthCheckRepository healthCheckRepository;

    public HealthCheckService(HealthCheckRepository healthCheckRepository) {
        this.healthCheckRepository = healthCheckRepository;
    }

    public HealthCheck healthCheck() {
        long activeSessions = healthCheckRepository.countApplicationConnections();

        if (activeSessions > 1) {
            return new HealthCheck("Welcome to my projet", ApplicationStatus.Ok, healthCheckRepository.countApplicationConnections());
        } else {
            return new HealthCheck("This projet n'est pas fonctionnelle", ApplicationStatus.KO, healthCheckRepository.countApplicationConnections());
        }
    }
}
