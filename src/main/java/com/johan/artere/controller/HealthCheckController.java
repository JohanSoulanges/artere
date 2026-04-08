package com.johan.artere.controller;

import com.johan.artere.model.HealthCheck;
import com.johan.artere.service.HealthCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    final private HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping("/healthcheck")
    public HealthCheck healthCheck() {
        return healthCheckService.healthCheck();
    }
}
