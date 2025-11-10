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

public class GetByIdIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void getById() throws Exception {
        ProjectEntity project1 = new ProjectEntity();
        project1.setName("Office Building");
        project1.setResponsibleEmployeeId(1L);
        project1.setClientId(1L);
        project1.setClientContactName("John Doe");
        project1.setComment("Construction project");
        project1.setStartDate(LocalDate.of(2025, 10, 1));
        project1.setPlannedEndDate(LocalDate.of(2026, 3, 31));
        project1.setQualificationIds(List.of(1L));

        project1 = this.projectRepository.saveAndFlush(project1);

        this.mockMvc.perform(get("/api/v1/projects/" + project1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Office Building")))
                .andExpect(jsonPath("responsibleEmployeeId", is(1)))
                .andExpect(jsonPath("clientId", is(1)))
                .andExpect(jsonPath("clientContactName", is("John Doe")))
                .andExpect(jsonPath("comment", is("Construction project")))
                .andExpect(jsonPath("startDate", is("2025-10-01")))
                .andExpect(jsonPath("plannedEndDate", is("2026-03-31")))
                .andExpect(jsonPath("qualificationIds[0]", is(1)));
    }
}
