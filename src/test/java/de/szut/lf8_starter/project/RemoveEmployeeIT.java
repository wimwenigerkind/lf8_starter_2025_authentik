package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.employee.EmployeeRepository;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RemoveEmployeeIT extends AbstractIntegrationTest {

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
    void removeEmployeeFromProject() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Test Project");
        project.setResponsibleEmployeeId(1L);
        project.setClientId(1L);
        project.setClientContactName("Jane Smith");
        project.setComment("Test project for employee removal");
        project.setStartDate(LocalDate.of(2025, 10, 1));
        project.setPlannedEndDate(LocalDate.of(2026, 3, 31));

        ProjectEntity savedProject = this.projectRepository.saveAndFlush(project);

        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmployeeId(202L);
        employee.setRoleId(2L);
        employee.setStartDate(LocalDate.of(2025, 10, 1));
        employee.setEndDate(LocalDate.of(2025, 12, 31));
        employee.setProject(savedProject);

        this.employeeRepository.saveAndFlush(employee);

        var employeesBefore = this.employeeRepository.findAll();
        assertThat(employeesBefore).hasSize(1);
        assertThat(employeesBefore.get(0).getEmployeeId()).isEqualTo(202L);
        assertThat(employeesBefore.get(0).getProject().getId()).isEqualTo(savedProject.getId());

        this.mockMvc.perform(delete("/api/v1/projects/" + savedProject.getId() + "/employees/202")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        var employeesAfter = this.employeeRepository.findAll();
        assertThat(employeesAfter).isEmpty();
    }
}