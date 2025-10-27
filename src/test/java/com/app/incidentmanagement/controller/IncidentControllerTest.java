package com.app.incidentmanagement.controller;

import com.app.incidentmanagement.model.Incident;
import com.app.incidentmanagement.model.IncidentStatus;
import com.app.incidentmanagement.model.Severity;
import com.app.incidentmanagement.service.IncidentService;
import com.app.incidentmanagement.dto.UpdateIncidentStatus;
import com.app.incidentmanagement.exceptions.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(IncidentController.class)
public class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentService incidentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Incident incident1;
    private Incident incident2;
    private Incident incident3;
    private Incident invalidIncident;
    private List<Incident> incidents;

    @BeforeEach
    public void setupIncidents() {
        incidents = new ArrayList<>();

        incident1 = new Incident();
        incident1.setIncidentId("INC1233211");
        incident1.setTitle("Test Incident 1");
        incident1.setDescription("Test Description 1");
        incident1.setReportedBy("Peter Parker");
        incident1.setSeverity(Severity.LOW);
        incident1.setIncidentStatus(IncidentStatus.OPEN);
        incident1.setTimestamp(LocalDateTime.now());

        incident2 = new Incident();
        incident2.setIncidentId("INC9999999");
        incident2.setTitle("Test Incident 2");
        incident2.setDescription("Test Description 2");
        incident2.setReportedBy("Aragorn, Son of Arathorn");
        incident2.setSeverity(Severity.HIGH);
        incident2.setIncidentStatus(IncidentStatus.IN_PROGRESS);
        incident2.setTimestamp(LocalDateTime.now());

        incident3 = new Incident();
        incident3.setIncidentId("INC4242424242");
        incident3.setTitle("Test Incident 3");
        incident3.setDescription("Test Description 3");
        incident3.setReportedBy("You");
        incident3.setSeverity(Severity.HIGH);
        incident3.setIncidentStatus(IncidentStatus.RESOLVED);
        incident3.setTimestamp(LocalDateTime.now());

        invalidIncident = new Incident();
        invalidIncident.setIncidentId("INC000000");
        invalidIncident.setTitle("Invalid Incident");
        invalidIncident.setDescription("Invalid Incident");
        invalidIncident.setSeverity(Severity.LOW);
        invalidIncident.setIncidentStatus(IncidentStatus.OPEN);
        invalidIncident.setTimestamp(LocalDateTime.now());

        incidents.add(incident1);
        incidents.add(incident2);
        incidents.add(incident3);
    }

    @Test
    public void testGetIncidents() throws Exception {

        // Get All
        when(incidentService.getIncidents(null, null)).thenReturn(incidents);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/incidents"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].incidentId", containsInAnyOrder(
                        "INC1233211",
                        "INC9999999",
                        "INC4242424242")));

        // Get Severity High
        when(incidentService.getIncidents(Severity.HIGH, null)).thenReturn(
                incidents.stream().filter(i -> i.getSeverity() == Severity.HIGH).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/incidents?severity=HIGH"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].incidentId", containsInAnyOrder(
                        "INC9999999",
                        "INC4242424242")));

        // Get Incident Status Open
        when(incidentService.getIncidents(null, IncidentStatus.OPEN)).thenReturn(
                incidents.stream().filter(i -> i.getIncidentStatus() == IncidentStatus.OPEN).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/incidents?incidentStatus=OPEN"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].incidentId", containsInAnyOrder(
                        "INC1233211")));

        // Get Severity High and Incident Status Resolved
        when(incidentService.getIncidents(Severity.HIGH, IncidentStatus.RESOLVED)).thenReturn(
                incidents.stream().filter(i -> i.getSeverity() == Severity.HIGH && i.getIncidentStatus() == IncidentStatus.RESOLVED).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/incidents?severity=HIGH&incidentStatus=RESOLVED"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[*].incidentId", containsInAnyOrder(
                        "INC4242424242")));

        // Get Severity Medium (No Results)
        when(incidentService.getIncidents(Severity.MEDIUM, null)).thenReturn(
                incidents.stream().filter(i -> i.getSeverity() == Severity.MEDIUM).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/incidents?severity=MEDIUM"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.empty()));
    }

    // Successful Create Incident
    @Test
    public void testCreateIncident() throws Exception {
        when(incidentService.createIncident(any(Incident.class))).thenReturn(incident1);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incident1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.incidentId").value("INC1233211"));
    }

    // Unsuccessful Create Incident (Missing Fields)
    @Test
    public void testInputValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidIncident)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Successful Incident Status Patch
    @Test
    public void testUpdateIncidentStatus() throws Exception {
        UpdateIncidentStatus updateStatus = new UpdateIncidentStatus();
        updateStatus.setIncidentStatus(IncidentStatus.RESOLVED);

        when(incidentService.updateIncidentStatus(eq("INC1233211"), eq(IncidentStatus.RESOLVED)))
                .thenReturn(incident1);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/incidents/INC1233211")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStatus)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.incidentId").value("INC1233211"));
    }

    // Unsuccessful Incident Status Patch
    @Test
    public void testServiceErrorHandling() throws Exception {
        String invalidStatusJson = "{\"incidentStatus\": \"all good :)\"}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/incidents/INC1233211")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidStatusJson))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    // Successful Delete
    @Test
    public void testDeleteIncident() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/incidents/INC1233211"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(incidentService).deleteIncident("INC1233211");
    }

    // Unsuccessful Delete (ID not found)
    @Test
    public void testNotFoundErrorHandling() throws Exception {
        doThrow(new NotFoundException("Incident Id", "INC9999")).when(incidentService)
                .deleteIncident("INC9999");

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/incidents/INC9999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
