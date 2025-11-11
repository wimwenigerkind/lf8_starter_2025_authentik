package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateIT extends AbstractIntegrationTest {

    @MockBean
    private EmployeeClient employeeClient;

    @MockBean
    private ClientClient clientClient;

    @BeforeEach
    void setUpMocks() {
        when(employeeClient.employeeExists(anyLong())).thenReturn(true);
        when(employeeClient.employeeHasQualification(anyLong(), org.mockito.ArgumentMatchers.<Long>anyList())).thenReturn(true);
        when(employeeClient.isValidQualification(anyLong())).thenReturn(true);
        when(clientClient.clientExists(anyLong())).thenReturn(true);
    }

    @Test
    @WithMockUser(roles = "user")
    void updateProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Old Office Building");
        project.setResponsibleEmployeeId(1L);
        project.setClientId(1L);
        project.setClientContactName("John Doe");
        project.setComment("Old construction project");
        project.setStartDate(LocalDate.of(2025, 10, 1));
        project.setPlannedEndDate(LocalDate.of(2026, 3, 31));
        project.setQualificationIds(List.of(1L));

        ProjectEntity savedProject = this.projectRepository.saveAndFlush(project);

        final String updateContent = """
                {
                  "name": "New Office Complex",
                  "responsibleEmployeeId": 2,
                  "clientId": 2,
                  "clientContactName": "Jane Smith",
                  "comment": "Updated construction project",
                  "startDate": "2025-11-01",
                  "plannedEndDate": "2026-06-30",
                  "actualEndDate": null,
                  "qualificationIds": [2]
                }
                """;

        this.mockMvc.perform(put("/api/v1/projects/" + savedProject.getId())
                        .content(updateContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        ProjectEntity updatedProject = this.projectRepository.findById(savedProject.getId()).orElseThrow();
        assertThat(updatedProject.getName()).isEqualTo("New Office Complex");
        assertThat(updatedProject.getResponsibleEmployeeId()).isEqualTo(2L);
        assertThat(updatedProject.getClientId()).isEqualTo(2L);
        assertThat(updatedProject.getClientContactName()).isEqualTo("Jane Smith");
        assertThat(updatedProject.getComment()).isEqualTo("Updated construction project");
        assertThat(updatedProject.getStartDate()).isEqualTo(LocalDate.of(2025, 11, 1));
        assertThat(updatedProject.getPlannedEndDate()).isEqualTo(LocalDate.of(2026, 6, 30));
    }
}
