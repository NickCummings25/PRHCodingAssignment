package com.app.incidentmanagement.dto;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.IncidentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateIncident {

    @NotBlank(message = "title is required")
    @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters long")
    private String title;

    @NotBlank(message = "description is required")
    @Size(min = 10, max = 100, message = "description must be between 10 and 100 characters long")
    private String description;

    @NotBlank(message = "reportedBy is required")
    private String reported_by;

    @NotNull(message = "severity is required")
    private Severity severity;

    @NotNull(message = "incidentStatus is required")
    private IncidentStatus incidentStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReportedBy() {
        return reported_by;
    }

    public void setReportedBy(String reported_by) {
        this.reported_by = reported_by;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    public Incident toEntity() {
        Incident incident = new Incident();
        incident.setTitle(this.title);
        incident.setDescription(this.description);
        incident.setReportedBy(this.reported_by);
        incident.setSeverity(this.severity);
        incident.setIncidentStatus(this.incidentStatus);
        return incident;
    }
}