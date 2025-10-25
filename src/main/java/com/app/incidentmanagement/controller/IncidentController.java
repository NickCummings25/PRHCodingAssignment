package com.app.incidentmanagement.controller;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.IncidentList;
import com.app.incidentmanagement.service.IncidentService;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "v1/incidents")
public class IncidentController {
    @Resource
    private IncidentService incidentService;

    @GetMapping
    public ResponseEntity<List<Incident>> getIncidents() {
    }

    @PostMapping
    public ResponseEntity<Incident> createIncident(){
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Incident> updateStatus() {
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident() {
    }

}


