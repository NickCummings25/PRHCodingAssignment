package com.app.incidentmanagement.controller;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.IncidentStatus;
import com.app.incidentmanagement.service.IncidentService;
import com.app.incidentmanagement.dto.UpdateIncidentStatus;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "v1/incidents")
public class IncidentController {

    private static final Logger logger = LoggerFactory.getLogger(IncidentController.class);

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public List<Incident> getIncidents(
           @RequestParam(required = false) Severity severity,
           @RequestParam(required = false) IncidentStatus incidentStatus
    ) {
        logger.info("Getting incidents with severity: {} and incidentStatus: {}", severity, incidentStatus);
        return incidentService.getIncidents(severity, incidentStatus);
    }

    @PostMapping
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody Incident incident){
        Incident newIncident = incidentService.createIncident(incident);
        logger.info("Created incident with ID: {}", newIncident.getIncidentId());
        return new ResponseEntity<>(newIncident, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Incident> updateIncidentStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateIncidentStatus newStatus) {
        Incident updated = incidentService.updateIncidentStatus(id, newStatus.getIncidentStatus());
        logger.info("Successfully updated incident status for {} to {}", id, newStatus.getIncidentStatus());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable String id) {
        incidentService.deleteIncident(id);
        logger.info("Successfully deleted incident {}", id);
        return ResponseEntity.noContent().build();
    }

}


