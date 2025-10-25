package com.app.incidentmanagement.service;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.Status;
import com.app.incidentmanagement.repository.IncidentRepository;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public IncidentList getIncidents(Severity severity, Status status) {
    }
}