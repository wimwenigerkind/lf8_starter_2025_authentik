package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostIT extends AbstractIntegrationTest {

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
    void storeAndFind() throws Exception {
        final String content = """
                {
                  "name": "New Office Building",
                  "responsibleEmployeeId": 1,
                  "clientId": 1,
                  "clientContactName": "John Doe",
                  "comment": "Construction of new headquarters",
                  "startDate": "2025-10-01",
                  "plannedEndDate": "2026-03-31",
                  "actualEndDate": null,
                  "qualificationIds": [1]
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("/api/v1/projects")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name", is("New Office Building")))
                .andExpect(jsonPath("responsibleEmployeeId", is(1)))
                .andExpect(jsonPath("clientId", is(1)))
                .andExpect(jsonPath("clientContactName", is("John Doe")))
                .andExpect(jsonPath("comment", is("Construction of new headquarters")))
                .andExpect(jsonPath("startDate", is("2025-10-01")))
                .andExpect(jsonPath("plannedEndDate", is("2026-03-31")))
                .andExpect(jsonPath("qualificationIds[0]", is(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        final var loadedEntity = projectRepository.findById(id);

        assertThat(loadedEntity).isPresent();
        assertThat(loadedEntity.get().getId()).isEqualTo(id);
        assertThat(loadedEntity.get().getName()).isEqualTo("New Office Building");
        assertThat(loadedEntity.get().getResponsibleEmployeeId()).isEqualTo(1L);
        assertThat(loadedEntity.get().getClientId()).isEqualTo(1L);
        assertThat(loadedEntity.get().getClientContactName()).isEqualTo("John Doe");
        assertThat(loadedEntity.get().getComment()).isEqualTo("Construction of new headquarters");
    }
}