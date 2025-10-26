package com.app.incidentmanagement.dto;

import com.app.incidentmanagement.model.IncidentStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateIncidentStatus {

    @NotNull(message = "incidentStatus is required")
    private IncidentStatus incidentStatus;

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }
}