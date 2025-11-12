package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetProjectEmployeesIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void getProjectEmployees() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Software Development");
        project.setResponsibleEmployeeId(1L);
        project.setClientId(1L);
        project.setClientContactName("John Doe");
        project.setComment("Web application project");
        project.setStartDate(LocalDate.of(2025, 10, 1));
        project.setPlannedEndDate(LocalDate.of(2026, 3, 31));
        project.setQualificationIds(List.of(1L));

        EmployeeEntity employee1 = new EmployeeEntity();
        employee1.setResponsibleEmployeeId(101L);
        employee1.setRoleId(1L);
        employee1.setStartDate(LocalDate.of(2025, 10, 1));
        employee1.setProject(project);

        EmployeeEntity employee2 = new EmployeeEntity();
        employee2.setResponsibleEmployeeId(102L);
        employee2.setRoleId(2L);
        employee2.setStartDate(LocalDate.of(2025, 11, 1));
        employee2.setProject(project);

        List<EmployeeEntity> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        project.setEmployees(employees);

        ProjectEntity savedProject = this.projectRepository.saveAndFlush(project);

        this.mockMvc.perform(get("/api/v1/projects/" + savedProject.getId() + "/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId", is(savedProject.getId()), Long.class))
                .andExpect(jsonPath("$.projectName", is("Software Development")))
                .andExpect(jsonPath("$.employees", hasSize(2)))
                .andExpect(jsonPath("$.employees[0].employeeId", is(101)))
                .andExpect(jsonPath("$.employees[0].roleId", is(1)))
                .andExpect(jsonPath("$.employees[1].employeeId", is(102)))
                .andExpect(jsonPath("$.employees[1].roleId", is(2)));
    }
}