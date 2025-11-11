package de.szut.lf8_starter.project;

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

public class GetAllIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void getAllProjects() throws Exception {
        ProjectEntity project1 = new ProjectEntity();
        project1.setName("Office Building");
        project1.setResponsibleEmployeeId(1L);
        project1.setClientId(1L);
        project1.setClientContactName("John Doe");
        project1.setComment("Construction project");
        project1.setStartDate(LocalDate.of(2025, 10, 1));
        project1.setPlannedEndDate(LocalDate.of(2026, 3, 31));
        project1.setQualificationIds(List.of(1L));

        ProjectEntity project2 = new ProjectEntity();
        project2.setName("Software Development");
        project2.setResponsibleEmployeeId(2L);
        project2.setClientId(2L);
        project2.setClientContactName("Jane Smith");
        project2.setComment("Web application");
        project2.setStartDate(LocalDate.of(2025, 11, 1));
        project2.setPlannedEndDate(LocalDate.of(2026, 6, 30));
        project2.setQualificationIds(List.of(2L));

        this.projectRepository.saveAndFlush(project1);
        this.projectRepository.saveAndFlush(project2);

        this.mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Office Building")))
                .andExpect(jsonPath("$[0].responsibleEmployeeId", is(1)))
                .andExpect(jsonPath("$[0].clientId", is(1)))
                .andExpect(jsonPath("$[0].clientContactName", is("John Doe")))
                .andExpect(jsonPath("$[0].comment", is("Construction project")))
                .andExpect(jsonPath("$[0].startDate", is("2025-10-01")))
                .andExpect(jsonPath("$[0].plannedEndDate", is("2026-03-31")))
                .andExpect(jsonPath("$[0].qualificationIds[0]", is(1)))
                .andExpect(jsonPath("$[1].name", is("Software Development")))
                .andExpect(jsonPath("$[1].responsibleEmployeeId", is(2)))
                .andExpect(jsonPath("$[1].clientId", is(2)))
                .andExpect(jsonPath("$[1].clientContactName", is("Jane Smith")))
                .andExpect(jsonPath("$[1].comment", is("Web application")))
                .andExpect(jsonPath("$[1].startDate", is("2025-11-01")))
                .andExpect(jsonPath("$[1].plannedEndDate", is("2026-06-30")))
                .andExpect(jsonPath("$[1].qualificationIds[0]", is(2)));
    }
}
