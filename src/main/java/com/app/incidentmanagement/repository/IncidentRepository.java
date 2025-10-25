package com.app.incidentmanagement.repository;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.Status;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {

    List<Incident> findByIncidentID(String incident_id);

    List<Incident> findBySeverity(Status severity);

    List<Incident> findByStatus(Status status);

}
