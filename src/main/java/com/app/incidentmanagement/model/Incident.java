package com.app.incidentmanagement.model;

import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.model.IncidentStatus;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="INCIDENT_LIST")
public class Incident {

    @Id
    @Column(name = "incident_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String incidentId;

    @NotBlank(message = "title is required")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "description is required")
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
    private IncidentStatus status;

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
        return status;
    }

    public void setIncidentStatus(IncidentStatus status) {
        this.status = status;
    }
}