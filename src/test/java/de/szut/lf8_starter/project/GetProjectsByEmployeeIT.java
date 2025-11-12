        package de.szut.lf8_starter.project;

        import de.szut.lf8_starter.employee.EmployeeEntity;
        import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
        import org.junit.jupiter.api.Test;
        import org.springframework.security.test.context.support.WithMockUser;

        import java.time.LocalDate;
        import java.util.List;

        import static org.hamcrest.Matchers.hasSize;
        import static org.hamcrest.Matchers.is;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

        public class GetProjectsByEmployeeIT extends AbstractIntegrationTest {

            @Test
            @WithMockUser(roles = "user")
            void getProjectsForEmployee() throws Exception {

                ProjectEntity project1 = new ProjectEntity();
                project1.setName("Project A");
                project1.setResponsibleEmployeeId(1L);
                project1.setClientId(1L);
                project1.setStartDate(LocalDate.of(2025, 10, 1));
                project1.setPlannedEndDate(LocalDate.of(2026, 3, 31));

                EmployeeEntity e1 = new EmployeeEntity();
                e1.setEmployeeId(101L);
                e1.setRoleId(1L);
                e1.setStartDate(LocalDate.of(2025, 10, 1));
                e1.setProject(project1);

                project1.setEmployees(List.of(e1));

                ProjectEntity project2 = new ProjectEntity();
                project2.setName("Project B");
                project2.setResponsibleEmployeeId(2L);
                project2.setClientId(1L);
                project2.setStartDate(LocalDate.of(2025, 11, 1));
                project2.setPlannedEndDate(LocalDate.of(2026, 4, 30));

                EmployeeEntity e2 = new EmployeeEntity();
                e2.setEmployeeId(101L);
                e2.setRoleId(2L);
                e2.setStartDate(LocalDate.of(2025, 11, 1));
                e2.setProject(project2);

                project2.setEmployees(List.of(e2));

                ProjectEntity saved1 = this.projectRepository.saveAndFlush(project1);
                ProjectEntity saved2 = this.projectRepository.saveAndFlush(project2);

                this.mockMvc.perform(get("/api/v1/employees/{employeeId}/projects"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].projectId", is(saved1.getId()), Long.class))
                        .andExpect(jsonPath("$[0].projectName", is("Project A")))
                        .andExpect(jsonPath("$[1].projectId", is(saved2.getId()), Long.class))
                        .andExpect(jsonPath("$[1].projectName", is("Project B")));
            }

            @Test
            @WithMockUser(roles = "user")
            void getProjectForEmployeeById() throws Exception {

                ProjectEntity project = new ProjectEntity();
                project.setName("Single Project");
                project.setResponsibleEmployeeId(1L);
                project.setClientId(1L);
                project.setStartDate(LocalDate.of(2025, 12, 1));
                project.setPlannedEndDate(LocalDate.of(2026, 5, 31));

                EmployeeEntity e = new EmployeeEntity();
                e.setEmployeeId(202L);
                e.setRoleId(3L);
                e.setStartDate(LocalDate.of(2025, 12, 1));
                e.setProject(project);

                project.setEmployees(List.of(e));

                ProjectEntity saved = this.projectRepository.saveAndFlush(project);

                this.mockMvc.perform(get("/api/v1/employees/{employeeId}/projects/" + saved.getId()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.projectId", is(saved.getId()), Long.class))
                        .andExpect(jsonPath("$.projectName", is("Single Project")));
            }
        }