package com.app.incidentmanagement.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="INCIDENT_LIST")
public class Incident {

    @Id
    @Column(name = "incident_id")
    private String incidentId;

    @NotBlank(message = "title is required")
    @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters long")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "description is required")
    @Size(min = 10, max = 100, message = "description must be between 10 and 100 characters long")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "reportedBy is required")
    @Column(name = "reported_by")
    private String reported_by;

    @NotNull(message = "severity is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private Severity severity;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @NotNull(message = "incidentStatus is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "incident_status")
    private IncidentStatus incidentStatus;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    public Incident() {}

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public IncidentStatus getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(IncidentStatus incidentStatus) {
        this.incidentStatus = incidentStatus;
    }
}