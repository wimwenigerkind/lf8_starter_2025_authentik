package de.szut.lf8_starter.project;

import de.szut.lf8_starter.validation.ValidDateRange;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "project")
@ValidDateRange(startDateField = "startDate", endDateField = "plannedEndDate")
@ValidDateRange(startDateField = "startDate", endDateField = "actualEndDate")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "project name must not be blank")
    private String name;

    @NotNull(message = "project responsible employee must not be null")
    private Long responsibleEmployeeId;

    @NotNull(message = "project client must not be null")
    private Long clientId;

    @NotBlank(message = "project client contact name must not be blank")
    private String clientContactName;

    private String comment;

    @NotNull(message = "project startDate must not be null")
    private LocalDate startDate;

    @NotNull(message = "project plannedEndDate must not be null")
    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProjectEmployeeEntity> employees;
}