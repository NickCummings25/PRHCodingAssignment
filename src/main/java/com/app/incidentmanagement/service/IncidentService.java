package com.app.incidentmanagement.service;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.IncidentStatus;
import com.app.incidentmanagement.repository.IncidentRepository;
import com.app.incidentmanagement.exceptions.NotFoundException;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(Incident incident) {
        //Incident ID is created with INC and the current time in Miliseconds
        //This can be improved in the future with a more unique and incremental INC00000 code
        incident.setIncidentId("INC" + System.currentTimeMillis());
        return incidentRepository.save(incident);
    }

    public List<Incident> getIncidents(Severity severity, IncidentStatus status) {
        if (severity != null && status != null) {
            return incidentRepository.findBySeverityAndStatus(severity, status);
        } else if (severity != null) {
            return incidentRepository.findBySeverity(severity);
        } else if (status != null) {
            return incidentRepository.findByIncidentStatus(status);
        } else {
            return incidentRepository.findAll();
        }
    }

    public Incident updateIncidentStatus(String id, IncidentStatus newStatus) {
        return incidentRepository.findById(id).map(incident -> {
            incident.setIncidentStatus(newStatus);
            return incidentRepository.save(incident);
            })
                .orElseThrow(() -> new NotFoundException("Incident Id", id));
    }

    public void deleteIncident(String id) {
        if (!incidentRepository.existsById(id)) {
            throw new NotFoundException("Incident Id", id);
        }
        incidentRepository.deleteById(id);
    }

}