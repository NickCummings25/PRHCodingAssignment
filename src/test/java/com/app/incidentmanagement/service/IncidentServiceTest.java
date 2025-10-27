package com.app.incidentmanagement.service;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.IncidentStatus;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//TODO Testing should be expanded here to better test the error handling at the service layer

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    @Test
    void createIncidentSuccessful() {
        Incident incident1 = new Incident();
        incident1.setTitle("Service Incident 1");
        incident1.setDescription("Service Incident 1 Description");
        incident1.setReportedBy("Colonel Mustard");
        incident1.setSeverity(Severity.LOW);
        incident1.setIncidentStatus(IncidentStatus.OPEN);

        // This verifies the logic that creates an ID with INC + date in milliseconds within the Service Layer before accessing repository
        // Once that logic is changed to create the ID differently, this portion will need to be updated
        when(incidentRepository.save(any(Incident.class))).thenAnswer(invocation -> {
            Incident savedIncident = invocation.getArgument(0);
            assertNotNull(savedIncident.getIncidentId());
            assertTrue(savedIncident.getIncidentId().startsWith("INC"));
            return savedIncident;
        });

        Incident result = incidentService.createIncident(incident1);

        assertNotNull(result);
        assertNotNull(result.getIncidentId());
        assertTrue(result.getIncidentId().startsWith("INC"));
        assertEquals("Service Incident 1", result.getTitle());
        verify(incidentRepository, times(1)).save(any(Incident.class));
    }
}