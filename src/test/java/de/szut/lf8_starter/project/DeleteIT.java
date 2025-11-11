package de.szut.lf8_starter.project;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void happyPath() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setName("Software Development");
        project.setResponsibleEmployeeId(1L);
        project.setClientId(1L);
        project.setClientContactName("John Doe");
        project.setComment("Web application project");
        project.setStartDate(LocalDate.of(2025, 10, 1));
        project.setPlannedEndDate(LocalDate.of(2026, 3, 31));
        project.setQualificationIds(List.of(1L));

        ProjectEntity savedProject = this.projectRepository.saveAndFlush(project);

        this.mockMvc.perform(
                        delete("/api/v1/projects/" + savedProject.getId())
                                .with(csrf()))
                .andExpect(status().isNoContent());

        assertThat(projectRepository.findById(savedProject.getId()).isPresent()).isFalse();
    }
}