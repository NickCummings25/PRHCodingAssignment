package com.app.incidentmanagement.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.IncidentStatus;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {

    List<Incident> findBySeverity(Severity severity);

    List<Incident> findByIncidentStatus(IncidentStatus incidentStatus);

    List<Incident> findBySeverityAndIncidentStatus(Severity severity, IncidentStatus incidentStatus);

}
