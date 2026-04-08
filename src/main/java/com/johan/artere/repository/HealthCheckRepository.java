package com.johan.artere.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;


@Repository
public class HealthCheckRepository {

    final private EntityManager entityManager;

    public HealthCheckRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public long countApplicationConnections() {
        String applicationConnectionsQuery = "select count(*) from pg_stat_activity where application_name = 'PostgreSQL JDBC Driver'";
        return (Long) entityManager.createNativeQuery(applicationConnectionsQuery).getSingleResult();
    }
}
