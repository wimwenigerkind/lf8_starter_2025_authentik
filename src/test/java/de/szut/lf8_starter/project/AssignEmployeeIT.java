package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.employee.EmployeeRepository;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssignEmployeeIT extends AbstractIntegrationTest {

    @MockBean
    private EmployeeClient employeeClient;

    @MockBean
    private ClientClient clientClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUpMocks() {
        when(employeeClient.employeeExists(anyLong())).thenReturn(true);
        when(employeeClient.employeeHasQualification(anyLong(), org.mockito.ArgumentMatchers.<Long>anyList())).thenReturn(true);
        when(employeeClient.isValidQualification(anyLong())).thenReturn(true);
        when(clientClient.clientExists(anyLong())).thenReturn(true);
    }

    @Test
    @WithMockUser(roles = "user")
    void assignEmployeeToProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Test Project");
        project.setResponsibleEmployeeId(1L);
        project.setClientId(1L);
        project.setClientContactName("John Doe");
        project.setComment("Test project for employee assignment");
        project.setStartDate(LocalDate.of(2025, 10, 1));
        project.setPlannedEndDate(LocalDate.of(2026, 3, 31));
        project.setQualificationIds(List.of(1L));

        ProjectEntity savedProject = this.projectRepository.saveAndFlush(project);

        final String assignContent = """
                {
                  "employeeId": 101,
                  "qualification": "1",
                  "startDate": "2025-10-01",
                  "endDate": "2025-12-31"
                }
                """;

        this.mockMvc.perform(post("/api/v1/projects/" + savedProject.getId() + "/employees")
                        .content(assignContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        var employees = this.employeeRepository.findAll();
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmployeeId()).isEqualTo(101L);
        assertThat(employees.get(0).getRoleId()).isEqualTo(1L);
        assertThat(employees.get(0).getStartDate()).isEqualTo(LocalDate.of(2025, 10, 1));
        assertThat(employees.get(0).getEndDate()).isEqualTo(LocalDate.of(2025, 12, 31));
        assertThat(employees.get(0).getProject().getId()).isEqualTo(savedProject.getId());
    }
}