package com.app.incidentmanagement.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.IncidentStatus;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {

    List<Incident> findByIncidentId(String incidentId);

    List<Incident> findBySeverity(Severity severity);

    List<Incident> findByIncidentStatus(IncidentStatus incidentStatus);

    List<Incident> findBySeverityAndStatus(Severity severity, IncidentStatus status);

}
