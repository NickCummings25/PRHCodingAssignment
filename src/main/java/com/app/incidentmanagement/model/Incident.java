package com.app.incidentmanagement.model;
import java.time.LocalDateTime;

@Entity
@Table(name="INCIDENT_LIST")
public class Incident {

    @Id
    @Column(name = "incident_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String incident_id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "reported_by")
    private String reported_by;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private String severity;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private String status;

}