package de.szut.lf8_starter.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "project name must not be blank")
    private String name;

    @NotNull(message = "project responsible employee must not be null")
    private long responsibleEmployeeId;

    @NotNull(message = "project client must not be null")
    private long clientId;

    @NotBlank(message = "project client contact name must not be blank")
    private String clientContactName;

    private String comment;

    @NotNull(message = "project startDate must not be null")
    private LocalDate startDate;

    @NotNull(message = "project plannedEndDate must not be null")
    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ProjectEmployee> employees;

    @Data
    @Embeddable
    public static class ProjectEmployee {
        private long employeeId;
        private long roleId;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}